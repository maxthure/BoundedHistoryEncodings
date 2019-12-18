package computing;

import answerTerms.AnswerTerm;
import answerTerms.AnswerTermConjunction;
import answerTerms.AnswerTermDisjunction;
import answerTerms.Variable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Eval {

    public void eval( AnswerTerm answerTerm ) {
        if ( answerTerm == null ) return;
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:identifier.sqlite" );
            String sqlQuery = prepareQuery( answerTerm );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sqlQuery );
            while ( rs.next() ) {
                String column_1 = rs.getString( "column_1" );
                String column_2 = rs.getString( "column_2" );
                System.out.println( column_1 +"\t"+ column_2);
            }
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public String prepareQuery( AnswerTerm answerTerm ) {
        if(answerTerm instanceof Variable ){
            throw new IllegalArgumentException( answerTerm.toString() );
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ( answerTerm instanceof AnswerTermDisjunction ) {
            AnswerTermDisjunction answerTermDisjunction = (AnswerTermDisjunction) answerTerm;
            stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm1() ) );
            stringBuilder.append( " UNION " );
            stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm2() ) );
            return stringBuilder.toString();
        }
        else if ( answerTerm instanceof AnswerTermConjunction ) {
            AnswerTermConjunction answerTermConjunction = (AnswerTermConjunction) answerTerm;
            stringBuilder.append( "SELECT * FROM (" );
            stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm1() ) );
            stringBuilder.append( ") NATURAL JOIN (" );
            stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm2() ) );
            stringBuilder.append( ")" );
            return stringBuilder.toString();
        }
        else {
            return answerTerm.toString();
        }
    }

}
