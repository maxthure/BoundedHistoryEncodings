package computing;

import answerTerms.*;
import queries.*;

import java.security.PublicKey;

public class FunctionPhi {

    SubquerySaver subquerySaver;
    int pointInTime = 0;

    public FunctionPhi( SubquerySaver subquerySaver ) {
        this.subquerySaver = subquerySaver;
    }

    public DataPhi compute( int pointInTime, Query query ) {
        if ( pointInTime == 0 ) {
            AnswerTerm answerTerm = phiZero( query );
            return new DataPhi( pointInTime, query, answerTerm );
        } else {
            this.pointInTime = pointInTime;
            AnswerTerm answerTerm = replaceVariables( pointInTime, phiI( pointInTime, query ) );
            return new DataPhi( pointInTime, query, answerTerm );
        }
    }

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
        /*if ( answerTerm1.toString().equals( answerTerm2.toString() ) ) {
            return answerTerm1;
        }
        if ( answerTerm1 instanceof AnswerSet && answerTerm2 instanceof AnswerSet ) {
            AnswerSet aT1 = (AnswerSet) answerTerm1;
            AnswerSet aT2 = (AnswerSet) answerTerm2;
            if ( aT1.getAnswer().contains( aT2.getAnswer() ) ) {
                return new AnswerSet( new AtemporalQuery( aT1.getAnswer() ), -1 );
            }
            if ( aT2.getAnswer().contains( aT1.getAnswer() ) ) {
                return new AnswerSet( new AtemporalQuery( aT2.getAnswer() ), -1 );
            }
            return new AnswerSet( new AtemporalQuery( "(" + aT1.getAnswer() + " n " + aT2.getAnswer() + ")" ), -1 );
        }*/
        return new AnswerTermConjunction( answerTerm1, answerTerm2 );
    }

    AnswerTerm disjunction( AnswerTerm answerTerm1, AnswerTerm answerTerm2 ) {
        /*if ( answerTerm1.toString().equals( answerTerm2.toString() ) ) {
            return answerTerm1;
        }
        if ( answerTerm1 instanceof AnswerSet && answerTerm2 instanceof AnswerSet ) {
            AnswerSet aT1 = (AnswerSet) answerTerm1;
            AnswerSet aT2 = (AnswerSet) answerTerm2;
            if ( aT1.getAnswer().contains( aT2.getAnswer() ) ) {
                return new AnswerSet( new AtemporalQuery( aT1.getAnswer() ), -1 );
            }
            if ( aT2.getAnswer().contains( aT1.getAnswer() ) ) {
                return new AnswerSet( new AtemporalQuery( aT2.getAnswer() ), -1 );
            }
            return new AnswerSet( new AtemporalQuery( "(" + aT1.getAnswer() + " u " + aT2.getAnswer() + ")" ), -1 );
        }*/
        return new AnswerTermDisjunction( answerTerm1, answerTerm2 );
    }

}
