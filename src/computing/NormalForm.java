package computing;

import results.AnswerTerm;
import results.AnswerTermConjunction;
import results.AnswerTermDisjunction;
import results.Variable;

import java.util.HashMap;
import java.util.HashSet;

public class NormalForm {

    public AnswerTerm prepare( AnswerTerm answerTerm ) {
        HashSet<HashSet<AnswerTerm>> set = new HashSet<>();
        HashSet<AnswerTerm> temp = new HashSet<>();
        temp.add( answerTerm );
        set.add( temp );
        HashSet<HashSet<AnswerTerm>> tem = normalize( set );
        mapVars( tem );
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
                        if ( tD.getAnswerTerm1().toString().equals( "empty set" ) ) {
                            temp2.add( tD.getAnswerTerm2() );
                            toAdd.add( temp2 );
                            break;
                        }
                        if ( tD.getAnswerTerm2().toString().equals( "empty set" ) ) {
                            temp1.add( tD.getAnswerTerm1() );
                            toAdd.add( temp1 );
                            break;
                        }
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
                        if ( tC.getAnswerTerm1().toString().equals( "empty set" ) || tC.getAnswerTerm2().toString().equals( "empty set" ) ) {
                            break;
                        }
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

    private void mapVars( HashSet<HashSet<AnswerTerm>> set ) {
        HashMap<HashSet<AnswerTerm>, HashSet<AnswerTerm>> mappedVars = new HashMap<>();
        for ( HashSet<AnswerTerm> terms : set ) {
            HashSet<AnswerTerm> vars = new HashSet<>();
            HashSet<AnswerTerm> oths = new HashSet<>();
            for ( AnswerTerm term : terms ) {
                if ( term instanceof Variable ) {
                    vars.add( term );
                } else {
                    oths.add( term );
                }
            }
            if ( mappedVars.containsKey( vars ) ) {
                mappedVars.get( vars ).addAll( oths );
            } else {
                mappedVars.put( vars, oths );
            }
        }
        System.out.println( mappedVars );
    }
}
