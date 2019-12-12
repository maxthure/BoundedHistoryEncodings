package computing;

import answerTerms.AnswerTerm;
import answerTerms.AnswerTermConjunction;
import answerTerms.AnswerTermDisjunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Eval {

    public void eval( AnswerTerm answerTerm ) {

        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:identifier.sqlite" );
            String sqlQuery = prepareQuery( answerTerm );
            System.out.println( sqlQuery );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sqlQuery );
            while ( rs.next() ) {
                String columnID = rs.getString( "column_1" );
                System.out.println( columnID );
            }
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public String prepareQuery( AnswerTerm answerTerm ) {
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
