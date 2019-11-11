package computing;

import queries.*;

public class Phi extends Function {

    public Term compute( int timeInPoint, Query query ) {
        if ( timeInPoint == 0 ) {
            return phiZero( query );
        } else {
            Term termI = phiI( timeInPoint, query );
            return replaceVariables( timeInPoint, termI );
        }
    }

    private Term phiZero( Query query ) {
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
            return new AnswerTerm( query, 0 );
        }
    }

    private Term phiI( int i, Query query ) {
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
            return disjunction( phiZero( ( (Until) query ).getSubquery2() ), conjunction( phiI( i, ( (Until) query ).getSubquery1() ), new Variable( i, query ) ) );
        } else if ( query instanceof Since ) {
            if ( i <= 1 ) {
                return disjunction( phiI( i, ( (Since) query ).getSubquery2() ), conjunction( phiI( i, ( (Since) query ).getSubquery1() ), phiZero( query ) ) );
            } else {
                return disjunction( phiI( i, ( (Since) query ).getSubquery2() ), conjunction( phiI( i, ( (Since) query ).getSubquery1() ), phiI( i - 1, query ) ) );
            }

        } else {
            return new AnswerTerm( query, i );
        }
    }

    private Term replaceVariables( int timeInPoint, Term term ) {
        if ( term instanceof TermDisjunction ) {
            TermDisjunction tD = (TermDisjunction) term;
            return disjunction( replaceVariables( timeInPoint, tD.getTerm1() ), replaceVariables( timeInPoint, tD.getTerm2() ) );
        }
        if ( term instanceof TermConjunction ) {
            TermConjunction tC = (TermConjunction) term;
            return conjunction( replaceVariables( timeInPoint, tC.getTerm1() ), replaceVariables( timeInPoint, tC.getTerm2() ) );
        } else if ( term instanceof Variable ) {
            Variable v = (Variable) term;
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
        return term;
    }

}
