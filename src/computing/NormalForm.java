package computing;

import answerTerms.AnswerTerm;
import answerTerms.AnswerTermConjunction;
import answerTerms.AnswerTermDisjunction;

import java.util.HashSet;

public class NormalForm {

    public AnswerTerm prepare( AnswerTerm answerTerm ) {
        HashSet<HashSet<AnswerTerm>> set = new HashSet<>();
        HashSet<AnswerTerm> temp = new HashSet<>();
        temp.add( answerTerm );
        set.add( temp );
        HashSet<HashSet<AnswerTerm>> tem = normalize( set );
        return transformToAnswerTerms( tem );
    }

    private HashSet<HashSet<AnswerTerm>> normalize( HashSet<HashSet<AnswerTerm>> set ) {
        boolean checkAgain = true;
        HashSet<HashSet<AnswerTerm>> tempSet = new HashSet<>( set );
        HashSet<HashSet<AnswerTerm>> toRemove = new HashSet<>();
        HashSet<HashSet<AnswerTerm>> toAdd = new HashSet<>();

        while ( checkAgain ) {
            checkAgain = false;
            toRemove.clear();
            toAdd.clear();
            for ( HashSet<AnswerTerm> s : tempSet ) {
                for ( AnswerTerm a : s ) {
                    HashSet<AnswerTerm> temp = new HashSet<>( s );
                    if ( a instanceof AnswerTermDisjunction ) {
                        AnswerTermDisjunction tD = (AnswerTermDisjunction) a;
                        toRemove.add( s );
                        temp.remove( a );
                        HashSet<AnswerTerm> temp1 = new HashSet<>( temp );
                        HashSet<AnswerTerm> temp2 = new HashSet<>( temp );
                        temp1.add( tD.getAnswerTerm1() );
                        temp2.add( tD.getAnswerTerm2() );
                        toAdd.add( temp1 );
                        toAdd.add( temp2 );
                        checkAgain = true;
                        break;
                    } else if ( a instanceof AnswerTermConjunction ) {
                        AnswerTermConjunction tC = (AnswerTermConjunction) a;
                        toRemove.add( s );
                        temp.remove( a );
                        HashSet<AnswerTerm> temp1 = new HashSet<>( temp );
                        temp1.add( tC.getAnswerTerm1() );
                        temp1.add( tC.getAnswerTerm2() );
                        toAdd.add( temp1 );
                        checkAgain = true;
                        break;
                    }
                }
            }
            tempSet.removeAll( toRemove );
            tempSet.addAll( toAdd );
        }
        return tempSet;
    }

    public HashSet<HashSet<AnswerTerm>> normalizeCNF( HashSet<HashSet<AnswerTerm>> set ) {
        boolean checkAgain = true;
        HashSet<HashSet<AnswerTerm>> tempSet = new HashSet<>();
        tempSet.addAll( set );
        HashSet<HashSet<AnswerTerm>> toRemove = new HashSet<>();
        HashSet<HashSet<AnswerTerm>> toAdd = new HashSet<>();

        while ( checkAgain ) {
            checkAgain = false;
            toRemove.clear();
            toAdd.clear();
            for ( HashSet<AnswerTerm> s : tempSet ) {
                for ( AnswerTerm a : s ) {
                    HashSet<AnswerTerm> temp = new HashSet<>();
                    temp.addAll( s );
                    if ( a instanceof AnswerTermConjunction ) {
                        AnswerTermConjunction tC = (AnswerTermConjunction) a;
                        toRemove.add( s );
                        temp.remove( a );
                        HashSet<AnswerTerm> temp1 = new HashSet<>();
                        HashSet<AnswerTerm> temp2 = new HashSet<>();
                        temp1.addAll( temp );
                        temp2.addAll( temp );
                        temp1.add( tC.getAnswerTerm1() );
                        temp2.add( tC.getAnswerTerm2() );
                        toAdd.add( temp1 );
                        toAdd.add( temp2 );
                        checkAgain = true;
                        break;
                    } else if ( a instanceof AnswerTermDisjunction ) {
                        AnswerTermDisjunction tD = (AnswerTermDisjunction) a;
                        toRemove.add( s );
                        temp.remove( a );
                        HashSet<AnswerTerm> temp1 = new HashSet<>();
                        temp1.addAll( temp );
                        temp1.add( tD.getAnswerTerm1() );
                        temp1.add( tD.getAnswerTerm2() );
                        toAdd.add( temp1 );
                        checkAgain = true;
                        break;
                    }
                }
            }
            tempSet.removeAll( toRemove );
            tempSet.addAll( toAdd );
        }
        return tempSet;
    }

    private AnswerTerm transformToAnswerTerms( HashSet<HashSet<AnswerTerm>> set ) {
        HashSet<HashSet<AnswerTerm>> tempSet = new HashSet<>( set );

        if ( tempSet.isEmpty() ) return null;

        HashSet<AnswerTerm> s = tempSet.iterator().next();
        HashSet<AnswerTerm> tempS = new HashSet<>( s );

        if ( tempS.isEmpty() ) return null;

        AnswerTerm term = tempS.iterator().next();
        tempS.remove( term );
        AnswerTerm temp = transformAnswerTermConjunctions( term, tempS );
        tempSet.remove( s );

        return transformAnswerTermDisjunctions( temp, tempSet );
    }

    private AnswerTerm transformAnswerTermDisjunctions( AnswerTerm answerTerm, HashSet<HashSet<AnswerTerm>> set ) {
        HashSet<HashSet<AnswerTerm>> tempSet = new HashSet<>( set );

        if ( tempSet.isEmpty() ) return answerTerm;

        HashSet<AnswerTerm> s = tempSet.iterator().next();
        HashSet<AnswerTerm> tempS = new HashSet<>( s );

        if ( tempS.isEmpty() ) return answerTerm;

        AnswerTerm term = tempS.iterator().next();
        tempS.remove( term );
        AnswerTerm temp = transformAnswerTermConjunctions( term, tempS );
        tempSet.remove( s );

        return new AnswerTermDisjunction( answerTerm, transformAnswerTermDisjunctions( temp, tempSet ) );
    }

    private AnswerTerm transformAnswerTermConjunctions( AnswerTerm answerTerm, HashSet<AnswerTerm> set ) {
        HashSet<AnswerTerm> tempSet = new HashSet<>( set );

        if ( tempSet.isEmpty() ) return answerTerm;

        AnswerTerm temp = tempSet.iterator().next();
        tempSet.remove( temp );

        return new AnswerTermConjunction( answerTerm, transformAnswerTermConjunctions( temp, tempSet ) );
    }
}
