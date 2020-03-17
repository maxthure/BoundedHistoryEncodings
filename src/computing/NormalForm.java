package computing;

import results.*;

import java.util.HashMap;
import java.util.HashSet;

public class NormalForm {


    /**
     * This method converts the given answer term into an answer term in DNF (CNF possible)
     *
     * @param answerTerm
     * @return answer term in DNF
     */
    public AnswerTerm prepare( AnswerTerm answerTerm ) {
        HashSet<HashSet<AnswerTerm>> set = new HashSet<>();
        HashSet<AnswerTerm> temp = new HashSet<>();
        temp.add( answerTerm );
        set.add( temp );
        HashSet<HashSet<AnswerTerm>> tem = normalize( set );
        return transformToAnswerTerms( tem );
    }

    /**
     * This method converts the given answer term into an answer term in {@link DataNF}
     *
     * @param answerTerm
     * @return answer term in {@link DataNF}
     */
    public DataNF prepareNF( AnswerTerm answerTerm ) {
        HashSet<HashSet<AnswerTerm>> set = new HashSet<>();
        HashSet<AnswerTerm> temp = new HashSet<>();
        temp.add( answerTerm );
        set.add( temp );
        HashSet<HashSet<AnswerTerm>> tem = normalize( set );
        return mapVars( tem );
    }

    /**
     * Turns a set containing an answer term into sets resembling a DNF.
     * Because I could not think of a way to transform the AnswerTerms with binary operators, I chose to use Sets that
     * resemble the disjunctions and conjunctions (same for normalizeCNF).
     *
     * @param set containing an answer term
     * @return DNF
     */
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

    /**
     * This method turns the sets resembling a DNF into answer terms in DNF. This is necessary because the set can take
     * an arbitrary amount of arguments for both disjunction and conjunction (remember that those are only implicit
     * distinguished [[x,y,...],[x,y,...],...]) but AnswerTerms can only take two arguments
     * => [[[(x,(y,...)),(x,(y,...))],[(x,(y,...)),(x,(y,...))]], ... ]
     *
     * @param set resembling a DNF
     * @return answer term in DNF
     */
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

    /**
     * This method turns the sets resembling a DNF into a map aka {@link DataNF}
     *
     * @param set
     * @return answer term in {@link DataNF}
     */
    private DataNF mapVars( HashSet<HashSet<AnswerTerm>> set ) {
        System.out.println( "Set: " + set );
        DataNF mappedVars = new DataNF();
        for ( HashSet<AnswerTerm> terms : set ) {
            HashSet<Variable> vars = new HashSet<>();
            HashSet<DataNF> nfs = new HashSet<>();
            HashSet<AnswerTerm> oths = new HashSet<>();
            for ( AnswerTerm term : terms ) {
                if ( term instanceof Variable ) {
                    vars.add( (Variable) term );
                } else if ( term instanceof DataNF ) {
                    //TODO Was passiert, wenn term ein DataNF ist?!
                    nfs.add( (DataNF) term );
                } else {
                    oths.add( term );
                }
            }
            if ( !nfs.isEmpty() ) {
                /**
                 * If there already is a DataNF (created at a previous point in time) in the currently looked at
                 * conjunction  it will be updated and then added to the mappedVars. In order to update the DataNF all
                 * Variables and all AnswerTerms are added to each entry in the DataNF (due to the distributive
                 * properties of conjunctions and disjunctions).
                 * If there is no DataNF but the specific composition of Variables already exists in mappedVars, the
                 * corresponding Set of AnswerTerms is simply added to the Set of Set of AnswerTerms in that entry.
                 * If there is neither a DataNF nor an entry for the composition of Variables, a new entry will be
                 * generated by simply adding the Set of Vars as the Key and a Set of Set of AnswerTerms a the Value.
                 */
                for ( DataNF nf : nfs ) {
                    for ( HashSet<Variable> nfVar : nf.keySet() ) {
                        nfVar.addAll( vars );
                        for ( HashSet<AnswerTerm> ats : nf.get( nfVar ) ) {
                            ats.addAll( oths );
                        }
                    }
                    mappedVars.putAll( nf );
                }
            } else if ( mappedVars.containsKey( vars ) ) {
                mappedVars.get( vars ).add( oths );
            } else {
                HashSet<HashSet<AnswerTerm>> temp = new HashSet<>();
                temp.add( oths );
                mappedVars.put( vars, temp );
            }
        }
        System.out.println( "Dic: " + mappedVars );
        return mappedVars;
    }
}
