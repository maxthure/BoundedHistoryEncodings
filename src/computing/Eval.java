package computing;

import results.AnswerTerm;
import results.AnswerTermConjunction;
import results.AnswerTermDisjunction;
import results.Variable;

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
            if ( sqlQuery.isEmpty() ) {
                return;
            }
            //System.out.println( sqlQuery );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sqlQuery );
            while ( rs.next() ) {
                String column_1 = rs.getString( "column_1" );
                String column_2 = rs.getString( "column_2" );
                //System.out.println( column_1 + "\t" + column_2 );
            }
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public String prepareQuery( AnswerTerm answerTerm ) {
        if ( answerTerm instanceof Variable ) {
            return "";
            //throw new IllegalArgumentException( answerTerm.toString() );
        }
        StringBuilder stringBuilder = new StringBuilder();
        if ( answerTerm instanceof AnswerTermDisjunction ) {
            AnswerTermDisjunction answerTermDisjunction = (AnswerTermDisjunction) answerTerm;
            if ( answerTermDisjunction.getAnswerTerm1() instanceof Variable ) {
                stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm2() ) );
                return stringBuilder.toString();
            }
            if ( answerTermDisjunction.getAnswerTerm2() instanceof Variable ) {
                stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm1() ) );
                return stringBuilder.toString();
            }
            stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm1() ) );
            stringBuilder.append( " UNION " );
            stringBuilder.append( prepareQuery( answerTermDisjunction.getAnswerTerm2() ) );
            return stringBuilder.toString();
        } else if ( answerTerm instanceof AnswerTermConjunction ) {
            AnswerTermConjunction answerTermConjunction = (AnswerTermConjunction) answerTerm;
            if ( answerTermConjunction.getAnswerTerm1() instanceof AnswerTermConjunction ) {
                AnswerTermConjunction tC = (AnswerTermConjunction) answerTermConjunction.getAnswerTerm1();
                if ( tC.getAnswerTerm1() instanceof Variable || tC.getAnswerTerm2() instanceof Variable ) {
                    stringBuilder.append( "SELECT * FROM (" );
                    stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm2() ) );
                    stringBuilder.append( ")" );
                    return stringBuilder.toString();
                }
            }
            if ( answerTermConjunction.getAnswerTerm1() instanceof AnswerTermDisjunction ) {
                AnswerTermDisjunction tC = (AnswerTermDisjunction) answerTermConjunction.getAnswerTerm1();
                if ( tC.getAnswerTerm1() instanceof Variable || tC.getAnswerTerm2() instanceof Variable ) {
                    stringBuilder.append( "SELECT * FROM (" );
                    stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm2() ) );
                    stringBuilder.append( ")" );
                    return stringBuilder.toString();
                }
            }
            if ( answerTermConjunction.getAnswerTerm2() instanceof AnswerTermConjunction ) {
                AnswerTermConjunction tC = (AnswerTermConjunction) answerTermConjunction.getAnswerTerm2();
                if ( tC.getAnswerTerm1() instanceof Variable || tC.getAnswerTerm2() instanceof Variable ) {
                    stringBuilder.append( "SELECT * FROM (" );
                    stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm1() ) );
                    stringBuilder.append( ")" );
                    return stringBuilder.toString();
                }
            }
            if ( answerTermConjunction.getAnswerTerm2() instanceof AnswerTermDisjunction ) {
                AnswerTermDisjunction tC = (AnswerTermDisjunction) answerTermConjunction.getAnswerTerm2();
                if ( tC.getAnswerTerm1() instanceof Variable || tC.getAnswerTerm2() instanceof Variable ) {
                    stringBuilder.append( "SELECT * FROM (" );
                    stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm1() ) );
                    stringBuilder.append( ")" );
                    return stringBuilder.toString();
                }
            }
            stringBuilder.append( "SELECT * FROM (" );
            stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm1() ) );
            stringBuilder.append( ") NATURAL JOIN (" );
            stringBuilder.append( prepareQuery( answerTermConjunction.getAnswerTerm2() ) );
            stringBuilder.append( ")" );
            return stringBuilder.toString();
        } else {
            return answerTerm.toString();
        }
    }

}
