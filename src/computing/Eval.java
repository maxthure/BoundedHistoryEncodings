package computing;

import queries.*;
import results.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
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

    public int eval( DataNF answerTermNF ) {
        return queryDB( prepareQuery( answerTermNF ) );
    }

    /**
     * This method gets called to evaluate Subqueries.
     * It updates the given DataNF in a way that the to the Sets of variables corresponding AnswerSets are evaluated.
     *
     * @param phi
     * @return
     */
    public DataNF evalSubquery( DataPhi phi ) {
        DataNF answerTermNF = phi.getDataNF();
        DataNF temp = new DataNF();
        for ( HashSet<Variable> vars : answerTermNF.keySet() ) {
            String tempAnswer = queryDBforSubqery( answerTermSetIntoQuery( answerTermNF.get( vars ) ) );
            HashSet<AnswerTerm> tempAnswerTermSet = new HashSet<>();
            HashSet<HashSet<AnswerTerm>> tempSetOfAnswerTermSets = new HashSet<>();
            tempAnswerTermSet.add( new AnswerSet( phi.getQuery(), phi.getPointInTime(), tempAnswer ) );
            tempSetOfAnswerTermSets.add( tempAnswerTermSet );
            temp.put( vars, tempSetOfAnswerTermSets );
        }
        return temp;
    }

    private int queryDB( String query ) {
        int size = 0;
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:db.sqlite" );
            if ( query.isEmpty() ) {
                // TODO println entfernen
                //System.out.println( "Empty Query!" );
                return 0;
            }
            if ( query.equals( "bottom" ) || query.equals( "top" ) ) {
                // TODO println entfernen
                //System.out.println( query );
                if (query.equals( "top" )){
                    return -1;
                }
                return 0;
            }
            // TODO println entfernen
            //System.out.println( "SQL query: " + query );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery( query );
            while ( rs.next() ) {
                size++;
                //String column_1 = rs.getString( "url" );
                //String column_2 = rs.getString( "price" );
            }
            // TODO println entfernen
            System.out.println("Anzahl der Antworten: "+size);
            st.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return size;
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

            String temp = oneEntryIntoQuery( vars, answerTermNF.get( vars ) );
            if (temp.equals( "top" )){
                return "top";
            }
            /*
             * Check first if temp is empty
             */
            if ( !temp.isEmpty() && !temp.equals( "bottom" ) ) {
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
            /*
             * Depending on the query inside the {@link Variable} it can evaluate to different queries
             */
            if ( var.getQuery() instanceof StrongNext || var.getQuery() instanceof Until || var.getQuery() instanceof Eventually || var.getQuery() instanceof StrongNextPredicate || var.getQuery() instanceof UntilPredicate || var.getQuery() instanceof EventuallyPredicate ) {
                /*
                 * The variable evaluates to an empty set therefore returning nothing
                 */
                return "bottom";
            }
        }
        /*
         * If vars is empty or only contains Variables that contain WeakNext the part above will be skipped and only
         * the AnswerTerms will be considered.
         */
        String temp = answerTermSetIntoQuery( answerTerms );
        if ( temp.isEmpty() && !vars.isEmpty() ) {
            return "top";
        }
        return temp;
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

            String temp = answerTermsIntoQuery( terms );
            if (temp.equals( "top" )){
                return "top";
            }
            /*
             * Check if temp is empty or bottom
             */
            if ( !temp.isEmpty() && !temp.equals( "bottom" ) ) {
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
        boolean top = false;
        for ( AnswerTerm term : answerTerms ) {
            if ( !( term instanceof AnswerSet || term instanceof FilterAnswerSet ) ) {
                throw new IllegalArgumentException();
            }
            String temp = "bottom";
            if ( term instanceof AnswerSet ) {
                AnswerSet set = (AnswerSet) term;
                temp = set.getAnswer();
            }
            else {
                temp = prepareQuery( ( (FilterAnswerSet) term ).getAnswer() );
                if ( temp.isEmpty() || temp.equals( "bottom" ) ) {
                    temp = "bottom";
                } else if ( temp.equals( "top" ) ) {
                    temp = ( (FilterAnswerSet) term ).getQuery().getFilter().replace( "phi", "autos" );
                } else {
                    temp = ( (FilterAnswerSet) term ).getQuery().getFilter().replace( "phi", "(" + temp + ")" );
                }
            }
            /*
             * If bottom occurs in the AnswerTerms the whole join will be empty
             */
            if ( temp.equals( "bottom" ) ) {
                return "bottom";
            }
            /*
             * If top occurs in the AnswerTerms it has no impact on the join
             */
            if ( !temp.equals( "top" ) ) {
                if ( first ) {
                    first = false;
                    stringBuilder.append( "SELECT * FROM (" + temp + ")" );
                } else {
                    stringBuilder.append( " NATURAL JOIN (" );
                    stringBuilder.append( temp );
                    stringBuilder.append( ")" );
                }
            } else {
                top = true;
            }
        }
        if(first && top){
            return "top";
        }
        return stringBuilder.toString();
    }


    /* This whole section down here is dedicated to querying the database with subqueries so that every important information is saved for later use.
     * I couldn't use the normal queryDB function because of the huge difference in evaluating variables.
     */

    private String queryDBforSubqery( String query ) {
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:db.sqlite" );
            if ( query.isEmpty() ) {
                System.out.println( "Empty query!" );
                return "bottom";
            }

            if ( query.equals( "bottom" ) || query.equals( "top" ) ) {
                return query;
            }
            String unique_name = ( LocalDateTime.now().toString() ).replaceAll( "-|:|\\.", "_" );
            query = "CREATE TABLE result_table_" + unique_name + " AS " + query;
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
