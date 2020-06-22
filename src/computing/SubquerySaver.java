package computing;

import results.AnswerTerm;
import results.DataPhi;
import queries.*;
import results.Variable;

import java.util.HashSet;

/**
 * This class saves all necessary subqueries for future reference.
 */
public class SubquerySaver {

    private final HashSet<DataPhi> savedSubqueries = new HashSet<>();
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
            savedSubqueries.add( functionPhi.compute( pointInTime, ( (AlwaysPast) query ).getSubquery() ) );
            saveSubqueries( ( (AlwaysPast) query ).getSubquery(), pointInTime );
        } else if ( query instanceof Eventually ) {
            saveSubqueries( ( (Eventually) query ).getSubquery(), pointInTime );
        } else if ( query instanceof EventuallyPast ) {
            savedSubqueries.add( functionPhi.compute( pointInTime, ( (EventuallyPast) query ).getSubquery() ) );
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
        } else if ( query instanceof StrongNextPredicate ) {
            StrongNextPredicate q = (StrongNextPredicate) query;
            if ( q.getP() == 1 ) {
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                saveSubqueries( new StrongNextPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof StrongPreviousPredicate ) {
            StrongPreviousPredicate q = (StrongPreviousPredicate) query;
            if ( q.getP() == 1 ) {
                savedSubqueries.add( functionPhi.compute( pointInTime, q.getSubquery() ) );
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, new StrongPreviousPredicate( q.getSubquery(), q.getP() - 1 ) ) );
                saveSubqueries( new StrongPreviousPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof WeakNextPredicate ) {
            WeakNextPredicate q = (WeakNextPredicate) query;
            if ( q.getP() == 1 ) {
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                saveSubqueries( new WeakNextPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof WeakPreviousPredicate ) {
            WeakPreviousPredicate q = (WeakPreviousPredicate) query;
            if ( q.getP() == 1 ) {
                savedSubqueries.add( functionPhi.compute( pointInTime, q.getSubquery() ) );
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, new WeakPreviousPredicate( q.getSubquery(), q.getP() - 1 ) ) );
                saveSubqueries( new WeakPreviousPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof AlwaysPredicate ) {
            AlwaysPredicate q = (AlwaysPredicate) query;
            if ( q.getP() == 1 ) {
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                saveSubqueries( new AlwaysPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof AlwaysPastPredicate ) {
            AlwaysPastPredicate q = (AlwaysPastPredicate) query;
            if ( q.getP() == 1 ) {
                savedSubqueries.add( functionPhi.compute( pointInTime, q.getSubquery() ) );
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, new AlwaysPastPredicate( q.getSubquery(), q.getP() - 1 ) ) );
                saveSubqueries( new AlwaysPastPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof EventuallyPredicate ) {
            EventuallyPredicate q = (EventuallyPredicate) query;
            if ( q.getP() == 1 ) {
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                saveSubqueries( new EventuallyPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof EventuallyPastPredicate ) {
            EventuallyPastPredicate q = (EventuallyPastPredicate) query;
            if ( q.getP() == 1 ) {
                savedSubqueries.add( functionPhi.compute( pointInTime, q.getSubquery() ) );
                saveSubqueries( q.getSubquery(), pointInTime );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, new EventuallyPastPredicate( q.getSubquery(), q.getP() - 1 ) ) );
                saveSubqueries( new EventuallyPastPredicate( q.getSubquery(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof UntilPredicate ) {
            UntilPredicate q = (UntilPredicate) query;
            if ( q.getP() == 1 ) {
                saveSubqueries( q.getSubquery2(), pointInTime );
            } else {
                saveSubqueries( new UntilPredicate( q.getSubquery1(), q.getSubquery2(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof SincePredicate ) {
            SincePredicate q = (SincePredicate) query;
            if ( q.getP() == 1 ) {
                savedSubqueries.add( functionPhi.compute( pointInTime, q.getSubquery2() ) );
                saveSubqueries( q.getSubquery2(), pointInTime );
            } else {
                savedSubqueries.add( functionPhi.compute( pointInTime, new SincePredicate( q.getSubquery1(), q.getSubquery2(), q.getP() - 1 ) ) );
                saveSubqueries( new SincePredicate( q.getSubquery1(), q.getSubquery2(), q.getP() - 1 ), pointInTime );
            }
        } else if ( query instanceof Filter ) {
            saveSubqueries( ( (Filter) query ).getSubquery(), pointInTime );
        }
    }

    public HashSet<DataPhi> getSavedSubqueries() {
        return savedSubqueries;
    }

    public AnswerTerm getAnswerTermFromSavedQuery( int i, Query query ) {
        for ( DataPhi p : savedSubqueries ) {
            if (query instanceof PredicateQuery){
                if ( i == p.getPointInTime() && query.equals( p.getQuery() ) ) {
                    return p.getDataNF();
                }
            } else {
                if ( i == p.getPointInTime() && p.getQuery().equals( query ) ) {
                    return p.getDataNF();
                }
            }
        }
        //TODO println entfernen
        System.out.println( "Returned null! Query: "+ query );
        return null;
    }

    public void deleteAllOldSubqueries( int pointIntime ) {
        savedSubqueries.removeIf( phi -> phi.getPointInTime() < pointIntime );
    }
}
