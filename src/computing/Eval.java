package computing;

import results.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

/**
 * This class is supposed to evaluate a given DataNF. The way this class has to work at the end is dependent on
 * the input in the first instance and the way the given answer term is constructed.
 */
public class Eval {

    public void eval( DataNF answerTermNF ) {
        if ( answerTermNF == null ) return;
        for ( HashSet<Variable> vars : answerTermNF.keySet() ) {
            queryDB( answerTermNF.get( vars ) );
        }
    }

    private void queryDB( HashSet<HashSet<AnswerTerm>> answerTermNF ) {
        prepareQuery( answerTermNF );

        /*try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:identifier.sqlite" );
            String sqlQuery = prepareQuery( answerTermNF );
            if ( sqlQuery.isEmpty() ) {
                return;
            }
            // TODO println entfernen
            //System.out.println( sqlQuery );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( sqlQuery );
            while ( rs.next() ) {
                String column_1 = rs.getString( "column_1" );
                String column_2 = rs.getString( "column_2" );
                // TODO println entfernen
                //System.out.println( column_1 + "\t" + column_2 );
            }
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }

         */
    }


    public void prepareQuery( HashSet<HashSet<AnswerTerm>> answerTerms ) {
        StringBuilder stringBuilder = new StringBuilder();
        for ( HashSet<AnswerTerm> terms : answerTerms ) {
            boolean first = true;
            for ( AnswerTerm term : terms ) {
                if ( !(term instanceof AnswerSet) ) {
                    throw new IllegalArgumentException(  );
                }
                AnswerSet set = (AnswerSet) term;
                if(first){
                    first = false;
                    stringBuilder.append( set.getAnswer() );
                } else {
                    stringBuilder.append( " NATURAL JOIN (" );
                    stringBuilder.append( set.getAnswer() );
                    stringBuilder.append( ")" );
                }
            }
        }

        System.out.println( stringBuilder.toString() );

/*
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
 */
    }

}
