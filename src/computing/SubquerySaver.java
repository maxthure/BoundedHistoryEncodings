package computing;

import results.AnswerTerm;
import results.DataPhi;
import queries.*;

import java.util.HashSet;

/**
 * This class saves all necessary subqueries for future reference
 */
public class SubquerySaver {

    private HashSet<DataPhi> savedSubqueries = new HashSet<>();
    private FunctionPhi functionPhi = new FunctionPhi( this );

    public void saveSubqueries( DataPhi phi ) {
        saveSubqueries( phi.getQuery(), phi.getPointInTime() );
    }

    private void saveSubqueries( Query query, int pointInTime ) {
        if ( query instanceof Conjunction ) {
            saveSubqueries( ( (Conjunction) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Conjunction) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof Disjunction ) {
            saveSubqueries( ( (Disjunction) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Disjunction) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof StrongNext ) {
            saveSubqueries( ( (StrongNext) query ).getSubquery(), pointInTime );
        } else if ( query instanceof StrongPrevious ) {
            if ( pointInTime < 1 ) {
                savedSubqueries.add( functionPhi.compute( 0, ( (StrongPrevious) query ).getSubquery() ) );
                saveSubqueries( ( (StrongPrevious) query ).getSubquery(), 0 );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, ( (StrongPrevious) query ).getSubquery() ) );
                saveSubqueries( ( (StrongPrevious) query ).getSubquery(), pointInTime );
            }
        } else if ( query instanceof WeakNext ) {
            saveSubqueries( ( (WeakNext) query ).getSubquery(), pointInTime );
        } else if ( query instanceof WeakPrevious ) {
            if ( pointInTime < 1 ) {
                savedSubqueries.add( functionPhi.compute( 0, ( (WeakPrevious) query ).getSubquery() ) );
                saveSubqueries( ( (WeakPrevious) query ).getSubquery(), 0 );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, ( (WeakPrevious) query ).getSubquery() ) );
                saveSubqueries( ( (WeakPrevious) query ).getSubquery(), pointInTime );
            }
        } else if ( query instanceof Until ) {
            savedSubqueries.add( functionPhi.compute( 0, ( (Until) query ).getSubquery2() ) );
            saveSubqueries( ( (Until) query ).getSubquery1(), pointInTime );
            saveSubqueries( ( (Until) query ).getSubquery2(), pointInTime );
        } else if ( query instanceof Since ) {
            if ( pointInTime < 1 ) {
                savedSubqueries.add( functionPhi.compute( 0, query ) );
                saveSubqueries( ( (Since) query ).getSubquery1(), 0 );
                saveSubqueries( ( (Since) query ).getSubquery2(), 0 );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, query ) );
                saveSubqueries( ( (Since) query ).getSubquery1(), pointInTime );
                saveSubqueries( ( (Since) query ).getSubquery2(), pointInTime );
            }
        }
    }

    public HashSet<DataPhi> getSavedSubqueries() {
        return savedSubqueries;
    }

    public AnswerTerm getAnswerTermFromSavedQuery( int i, Query query ) {
        for ( DataPhi p : savedSubqueries ) {
            if ( i == p.getPointInTime() && p.getQuery().equals( query ) ) {
                return p.getDataNF();
                //System.out.println( "Subquery: " + p.getAnswerTerm() );
                //return p.getAnswerTerm();
            }
        }
        return null;
    }
}
