package computing;

import results.AnswerTerm;
import results.DataPhi;
import queries.*;
import results.Variable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

/**
 * This class saves all necessary subqueries for future reference.
 */
public class SubquerySaver {

    private final HashSet<DataPhi> savedSubqueries = new HashSet<>();
    private final HashSet<DataPhi> deletedSubqueries = new HashSet<>();
    private final FunctionPhi functionPhi = new FunctionPhi( this );

    public void saveSubqueries( DataPhi phi ) {
        saveSubqueries( phi.getQuery(), phi.getPointInTime() );
    }

    public void saveSubqueries( Query query, int pointInTime ) {
        if ( query instanceof Conjunction ) {
            saveSubqueries( ( (Conjunction) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Conjunction) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof Disjunction ) {
            saveSubqueries( ( (Disjunction) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Disjunction) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof StrongNext ) {
            saveSubqueries( ( (StrongNext) query ).getSubquery(), pointInTime );
        } else if ( query instanceof StrongPrevious ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, ( (StrongPrevious) query ).getSubquery() ) );
            saveSubqueries( ( (StrongPrevious) query ).getSubquery(), pointInTime );
        } else if ( query instanceof WeakNext ) {
            saveSubqueries( ( (WeakNext) query ).getSubquery(), pointInTime );
        } else if ( query instanceof WeakPrevious ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, ( (WeakPrevious) query ).getSubquery() ) );
            saveSubqueries( ( (WeakPrevious) query ).getSubquery(), pointInTime );
        } else if ( query instanceof Always ) {
            saveSubqueries( ( (Always) query ).getSubquery(), pointInTime );
        } else if ( query instanceof AlwaysPast ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, query ) );
            saveSubqueries( ( (AlwaysPast) query ).getSubquery(), pointInTime );
        } else if ( query instanceof Eventually ) {
            saveSubqueries( ( (Eventually) query ).getSubquery(), pointInTime );
        } else if ( query instanceof EventuallyPast ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, query ) );
            saveSubqueries( ( (EventuallyPast) query ).getSubquery(), pointInTime );
        } else if ( query instanceof Until ) {
            //TODO Dies hier müsste nicht gespeichert werden müssen
            //savedSubqueries.add( functionPhi.compute( pointInTime, query ) );
            saveSubqueries( ( (Until) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Until) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof Since ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, query ) );
            saveSubqueries( ( (Since) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Since) query ).getSubquery2(), pointInTime );
        }
    }

    public HashSet<DataPhi> getSavedSubqueries() {
        return savedSubqueries;
    }

    public AnswerTerm getAnswerTermFromSavedQuery( int i, Query query ) {
        for ( DataPhi p : savedSubqueries ) {
            if ( i == p.getPointInTime() && p.getQuery().equals( query ) ) {
                return p.getDataNF();
                //return p.getAnswerTerm();
            }
        }
        //TODO println entfernen
        System.out.println( "Returned null! Query: " + query );
        return null;
    }

    public void deleteAllOldSubqueries( int pointIntime ) {
        HashSet<DataPhi> toDelete = new HashSet<>();
        for ( DataPhi phi : savedSubqueries ) {
            if ( phi.getPointInTime() < pointIntime ) {
                deletedSubqueries.add( phi );
                toDelete.add( phi );
            }
        }
        savedSubqueries.removeAll( toDelete );
    }

    public void dropTables() {
        HashSet<DataPhi> toDelete = new HashSet<>();
        try {
            Class.forName( "org.sqlite.JDBC" );
            Connection conn = DriverManager.getConnection( "jdbc:sqlite:db.sqlite" );
            Statement st = conn.createStatement();
            for ( DataPhi phi : deletedSubqueries ) {
                for ( HashSet<Variable> k : phi.getDataNF().keySet() ) {
                    toDelete.add( phi );

                    for ( HashSet<AnswerTerm> h : phi.getDataNF().get( k ) ) {
                        for ( AnswerTerm t : h ) {
                            if ( t.toString().contains( "result" ) ) {
                                st.executeUpdate( "DROP TABLE " + t.toString().replace( "SELECT * FROM ", "" ) );
                            }
                        }
                    }

                }
            }
            st.close();
            conn.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        deletedSubqueries.removeAll( toDelete );
    }
}
