package computing;

import queries.*;
import results.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is supposed to evaluate a given DataNF. The way this class has to work at the end is dependent on
 * the input in the first instance and the way the given answer term is constructed.
 * <p>
 * Currently prepareQuery turns a {@link DataNF} into a SQL-Query one entry at a time (oneEntryIntoQuery()) and then
 * "UNION" them all
 * prepareQuery
 * oneEntryIntoQuery
 * answerTermSetIntoQuery
 * answerTermsIntoQuery
 */
public class Eval {

    public void eval( DataNF answerTermNF ) {
        queryDB( prepareQuery( answerTermNF ) );
    }

    /**
     * This method gets called to evaluate Subqueries.
     * It updates the given DataNF in a way that the to the Sets of variables corresponding AnswerSets are evaluated.
     * @param answerTermNF
     * @return
     */
    public DataNF evalSubquery( DataNF answerTermNF ) {
        DataNF temp = new DataNF();
        for ( HashSet<Variable> vars : answerTermNF.keySet() ) {
            String tempAnswer = queryDBforSubqery( answerTermSetIntoQuery( answerTermNF.get( vars ) ) );
            HashSet<AnswerTerm> tempAnswerTermSet = new HashSet<>();
            HashSet<HashSet<AnswerTerm>> tempSetOfAnswerTermSets = new HashSet<>();
            tempAnswerTermSet.add( new AnswerSet( tempAnswer ) );
            tempSetOfAnswerTermSets.add( tempAnswerTermSet );
            temp.put( vars, tempSetOfAnswerTermSets);
        }
        return temp;
    }

    private void queryDB( String query ) {
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:identifier.sqlite" );
            if ( query.isEmpty() ) {
                return;
            }
            // TODO println entfernen
            System.out.println( query );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( query );
            while ( rs.next() ) {
                String column_1 = rs.getString( "column_1" );
                String column_2 = rs.getString( "column_2" );
                // TODO println entfernen
                System.out.println( column_1 + "\t" + column_2 );
            }
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * This method prepares the query.
     * For each set of {@link Variable}s it calls oneEntryIntoQuery() and then unions the returned queries if they are not empty.
     *
     * @param answerTermNF A {@link DataNF} that is to be turned into a query.
     * @return The query that was build.
     */
    private String prepareQuery( DataNF answerTermNF ) {
        StringBuilder stringBuilder = new StringBuilder();

        boolean firstUnion = true;
        for ( HashSet<Variable> vars : answerTermNF.keySet() ) {
            /*
             * Check first if temp is empty
             */
            String temp = oneEntryIntoQuery( vars, answerTermNF.get( vars ) );
            if ( !temp.isEmpty() ) {
                if ( firstUnion ) {
                    firstUnion = false;

                } else {
                    stringBuilder.append( " UNION " );
                }
                stringBuilder.append( temp );
            }


        }
        return stringBuilder.toString();
    }

    //TODO This step is wrong unless you do the evaluation at the end. For saving subqueries only the AnswerTerms have to be considered and saved in the DataNF as 1 AnswerSet.

    /**
     * This method turns one entry of the DataNF into a query.
     * Depending of the type of query that is saved in the {@link Variable} it has different effects on the query.
     * If there are no {@code Variable}s the query is not affected.
     * The basic query is calculated by turning the corresponding {@link AnswerTerm}s into a query (answerTermSetIntoQuery())
     *
     * @param vars        A set of variables from a {@link DataNF}
     * @param answerTerms The corresponding set of sets of {@link AnswerTerm}s
     * @return The query that was build at this point.
     */
    private String oneEntryIntoQuery( HashSet<Variable> vars, HashSet<HashSet<AnswerTerm>> answerTerms ) {
        for ( Variable var : vars ) {
            if ( !( var.getQuery() instanceof StrongNext || var.getQuery() instanceof WeakNext || var.getQuery() instanceof Until ) ) {
                throw new IllegalArgumentException();
            }
            /*
             * Depending on the query inside the {@link Variable} it can evaluate to different queries
             */
            if ( var.getQuery() instanceof StrongNext || var.getQuery() instanceof Until ) {
                /*
                 * The variable evaluates to an empty set therefore returning nothing
                 */
                return "";
            }
        }
        /*
         * If vars is empty or only contains Variables that contain WeakNext the part above will be skipped and only
         * the AnswerTerms will be considered.
         */
        return answerTermSetIntoQuery( answerTerms );
    }

    /**
     * This method turns a set of sets of AnswerTerms into a query.
     * For each set of {@code AnswerTerm}s it calls answerTermsIntoQuery() and then unions the returned queries if they are not empty.
     *
     * @param answerTerms The set of sets of {@link AnswerTerm}s that is to be turned into a query
     * @return The build query at this point.
     */
    private String answerTermSetIntoQuery( HashSet<HashSet<AnswerTerm>> answerTerms ) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstUnion = true;
        for ( HashSet<AnswerTerm> terms : answerTerms ) {
            /*
             * Check first if temp is empty
             */
            String temp = answerTermsIntoQuery( terms );
            if ( !temp.isEmpty() ) {
                if ( firstUnion ) {
                    firstUnion = false;
                } else {
                    stringBuilder.append( " UNION " );
                }
                stringBuilder.append( temp );
            }
        }
        return stringBuilder.toString();
    }

    /**
     * This method turns a set of AnswerTerms into a query.
     * If a {@code AnswerTerm} is empty it returns an empty query.
     * If a {@code AnswerTerm} is full it returns the normal query as a full {@code AnswerTerm} has no effect.
     * The normal query is build by joining all the {@code AnswerTerm}s.
     *
     * @param answerTerms The set of {@link AnswerTerm}s that is to be turned into a query.
     * @return The query build at this point.
     */
    private String answerTermsIntoQuery( HashSet<AnswerTerm> answerTerms ) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for ( AnswerTerm term : answerTerms ) {
            if ( !( term instanceof AnswerSet ) ) {
                throw new IllegalArgumentException();
            }
            AnswerSet set = (AnswerSet) term;
            String temp = set.getAnswer();
            /*
             * If emtpy occurs in the AnswerTerms the whole join will be empty
             */
            if ( temp.equals( "empty" ) ) {
                return "";
            }
            /*
             * If full occurs in the AnswerTerms it has no impact on the join
             */
            if ( !temp.equals( "full" ) ) {
                if ( first ) {
                    first = false;
                    stringBuilder.append( temp );

                } else {
                    stringBuilder.append( " NATURAL JOIN (" );
                    stringBuilder.append( temp );
                    stringBuilder.append( ")" );
                }
            }
        }
        return stringBuilder.toString();
    }


    /* This whole section down here is dedicated to querying the database with subqueries so that every important information is saved for later use.
     * I couldn't use the normal queryDB function because of the huge difference in evaluating variables.
     */

    private String queryDBforSubqery( String query ) {
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:identifier.sqlite" );
            if ( query.isEmpty() ) {
                return "";
            }
            String unique_name = ( LocalDateTime.now().toString() ).replaceAll( "-|:|\\.", "_" );
            query = "CREATE TABLE result_table_" + unique_name + " AS " + query;
            // TODO println entfernen
            System.out.println( unique_name );
            System.out.println( query );
            Statement st = conn.createStatement();
            st.executeUpdate( query );
            st.close();
            return "SELECT * FROM result_table_" + unique_name;

        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return "";
    }

}
