package computing;

import results.*;
import queries.*;

/**
 * This class calculates PhiI (or Phi0)
 */
public class FunctionPhi {

    private final SubquerySaver subquerySaver;
    private final NormalForm normalForm = new NormalForm();
    int pointInTime = 0;

    public FunctionPhi( SubquerySaver subquerySaver ) {
        this.subquerySaver = subquerySaver;
    }

    /**
     * This is the function that is called to compute PhiI.
     * It determines if Phi0 or PhiI is calculated.
     * If PhiI is to be calculated, variables from past points in time have to be replaced with the corresponding answer
     * at the current point in time.
     * Furthermore before returning the {@link DataPhi} the AnswerTerms are transformed into DNF and {@link DataNF}.
     *
     * @param pointInTime An int that describes the current point in time
     * @param query The {@link Query} to be computed
     * @return A {@code DataPhi} that represents the answer term to the query at the given point in time. It contains:
     * <li>the time in point
     * <li>the actual query
     * <li>the {@link AnswerTerm} in DNF
     * <li>the AnswerTerm in {@code DataNF}
     */
    public DataPhi compute( int pointInTime, Query query ) {
        if ( pointInTime == 0 ) {
            AnswerTerm answerTerm = phiZero( query );
            return new DataPhi( pointInTime, query, normalForm.prepare( answerTerm ), normalForm.prepareNF( answerTerm ) );
        } else {
            this.pointInTime = pointInTime;
            AnswerTerm answerTerm = replaceVariables( pointInTime, phiI( pointInTime, query ) );
            return new DataPhi( pointInTime, query, normalForm.prepare( answerTerm ), normalForm.prepareNF( answerTerm ) );
        }
    }

    /**
     * This function recursively calculates Phi0 as defined in the paper.
     *
     * @param query The {@link Query} to be computed
     * @return The {@link AnswerTerm} for the query at time in point 0
     */
    private AnswerTerm phiZero( Query query ) {
        if ( pointInTime > 0 ) {
            return subquerySaver.getAnswerTermFromSavedQuery( 0, query );
        }
        if ( query instanceof Conjunction ) {
            return conjunction( phiZero( ( (Conjunction) query ).getSubquery1() ), phiZero( ( (Conjunction) query ).getSubquery2() ) );
        } else if ( query instanceof Disjunction ) {
            return disjunction( phiZero( ( (Disjunction) query ).getSubquery1() ), phiZero( ( (Disjunction) query ).getSubquery2() ) );
        } else if ( query instanceof StrongNext ) {
            return new Variable( 0, query );
        } else if ( query instanceof WeakNext ) {
            return new Variable( 0, query );
        } else if ( query instanceof Until ) {
            return disjunction( phiZero( ( (Until) query ).getSubquery2() ), conjunction( phiZero( ( (Until) query ).getSubquery1() ), new Variable( 0, query ) ) );
        } else if ( query instanceof Since ) {
            return phiZero( ( (Since) query ).getSubquery2() );
        } else {
            return new AnswerSet( query, 0 );
        }
    }

    /**
     * This function recursively calculates PhiI as defined in the paper.
     *
     * @param i An int that determines the point in time
     * @param query The {@link Query} to be computed
     * @return The answer term for the query at time in point i
     */
    private AnswerTerm phiI( int i, Query query ) {
        if ( i < pointInTime ) {
            return subquerySaver.getAnswerTermFromSavedQuery( i, query );
        }
        if ( query instanceof Conjunction ) {
            return conjunction( phiI( i, ( (Conjunction) query ).getSubquery1() ), phiI( i, ( (Conjunction) query ).getSubquery2() ) );
        } else if ( query instanceof Disjunction ) {
            return disjunction( phiI( i, ( (Disjunction) query ).getSubquery1() ), phiI( i, ( (Disjunction) query ).getSubquery2() ) );
        } else if ( query instanceof StrongNext ) {
            return new Variable( i, query );
        } else if ( query instanceof StrongPrevious ) {
            if ( i <= 1 ) {
                return phiZero( ( (StrongPrevious) query ).getSubquery() );
            } else {
                return phiI( i - 1, ( (StrongPrevious) query ).getSubquery() );
            }
        } else if ( query instanceof WeakNext ) {
            return new Variable( i, query );
        } else if ( query instanceof WeakPrevious ) {
            if ( i <= 1 ) {
                return phiZero( ( (WeakPrevious) query ).getSubquery() );
            } else {
                return phiI( i - 1, ( (WeakPrevious) query ).getSubquery() );
            }
        } else if ( query instanceof Until ) {
            if ( i <= 1 ) {
                return disjunction( phiZero( ( (Until) query ).getSubquery2() ), conjunction( phiI( i, ( (Until) query ).getSubquery1() ), new Variable( i, query ) ) );
            } else {
                return disjunction( phiI( i, ( (Until) query ).getSubquery2() ), conjunction( phiI( i, ( (Until) query ).getSubquery1() ), new Variable( i, query ) ) );
            }

        } else if ( query instanceof Since ) {
            if ( i <= 1 ) {
                return disjunction( phiI( i, ( (Since) query ).getSubquery2() ), conjunction( phiI( i, ( (Since) query ).getSubquery1() ), phiZero( query ) ) );
            } else {
                return disjunction( phiI( i, ( (Since) query ).getSubquery2() ), conjunction( phiI( i, ( (Since) query ).getSubquery1() ), phiI( i - 1, query ) ) );
            }

        } else {
            return new AnswerSet( query, i );
        }
    }

    /**
     * This method replaces variables from past points in time with the corresponding answers for the current point in time
     *
     * @param timeInPoint An int that describes the current point in time
     * @param answerTerm An {@code AnswerTerm} with old Variables
     * @return An {@code AnswerTerm} with old Variables replaced by corresponding answer at current point in time
     */
    private AnswerTerm replaceVariables( int timeInPoint, AnswerTerm answerTerm ) {
        if ( answerTerm instanceof AnswerTermDisjunction ) {
            AnswerTermDisjunction tD = (AnswerTermDisjunction) answerTerm;
            return disjunction( replaceVariables( timeInPoint, tD.getAnswerTerm1() ), replaceVariables( timeInPoint, tD.getAnswerTerm2() ) );
        }
        if ( answerTerm instanceof AnswerTermConjunction ) {
            AnswerTermConjunction tC = (AnswerTermConjunction) answerTerm;
            return conjunction( replaceVariables( timeInPoint, tC.getAnswerTerm1() ), replaceVariables( timeInPoint, tC.getAnswerTerm2() ) );
        } else if ( answerTerm instanceof Variable ) {
            Variable v = (Variable) answerTerm;
            if ( v.getPointInTime() < timeInPoint ) {
                Query q = v.getQuery();
                Query subQ;
                if ( q instanceof StrongNext ) {
                    subQ = ( (StrongNext) q ).getSubquery();
                } else if ( q instanceof WeakNext ) {
                    subQ = ( (WeakNext) q ).getSubquery();
                } else {
                    subQ = q;
                }

                if ( timeInPoint < 1 ) {
                    return replaceVariables( timeInPoint, phiZero( subQ ) );
                }
                return replaceVariables( timeInPoint, phiI( v.getPointInTime() + 1, subQ ) );
            }
        }
        return answerTerm;
    }


    AnswerTerm conjunction( AnswerTerm answerTerm1, AnswerTerm answerTerm2 ) {
        return new AnswerTermConjunction( answerTerm1, answerTerm2 );
    }

    AnswerTerm disjunction( AnswerTerm answerTerm1, AnswerTerm answerTerm2 ) {
        return new AnswerTermDisjunction( answerTerm1, answerTerm2 );
    }

}
