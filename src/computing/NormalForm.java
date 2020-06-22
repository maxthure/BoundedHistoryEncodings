package computing;

import queries.Filter;
import results.*;

import java.util.HashSet;

public class NormalForm {


    // For a version that includes the AnswerTerm in DataPhi:

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
     * @param answerTerm The {@link AnswerTerm} that is to be transformed in NF
     * @return Answer term in {@code DataNF}
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
     * Turns a set containing an {@link AnswerTerm} into sets resembling a DNF.
     * Because I could not think of a way to transform the {@code AnswerTerm}s with binary operators, I chose to use
     * sets that resemble the disjunctions and conjunctions (same for normalizeCNF).
     *
     * @param set A Set containing the {@code AnswerTerm} that is to be transformed
     * @return A DNF of the {@code AnswerTerm} but with sets
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
                        if ( tD.getAnswerTerm1().toString().equals( "bottom" ) ) {
                            temp2.add( tD.getAnswerTerm2() );
                            toAdd.add( temp2 );
                            break;
                        }
                        if ( tD.getAnswerTerm2().toString().equals( "bottom" ) ) {
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
                        if ( tC.getAnswerTerm1().toString().equals( "bottom" ) || tC.getAnswerTerm2().toString().equals( "bottom" ) ) {
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
/*
    public HashSet<HashSet<AnswerTerm>> normalizeCNF( HashSet<HashSet<AnswerTerm>> set ) {
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
                    if ( a instanceof AnswerTermConjunction ) {
                        AnswerTermConjunction tC = (AnswerTermConjunction) a;
                        toRemove.add( s );
                        temp.remove( a );
                        HashSet<AnswerTerm> temp1 = new HashSet<>( temp );
                        HashSet<AnswerTerm> temp2 = new HashSet<>( temp );
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
                        HashSet<AnswerTerm> temp1 = new HashSet<>( temp );
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
*/


    // For a version that includes the AnswerTerm in DataPhi:

    /**
     * This method turns the sets resembling a DNF into {@code AnswerTerm}s in DNF.
     * This is necessary because the set can take an arbitrary amount of arguments for both disjunction and conjunction
     * (remember that those are only implicit distinguished [[x,y],[x,y,...],...]) but {@link AnswerTerm}s can only
     * take two arguments => [[[(x n y) u (x n (y n ...))] u [(x n (y n ...)) u (x n (y n ...))]] u ... ]
     *
     * @param set A set of sets resembling a DNF
     * @return An {@code AnswerTerm} in DNF
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
     * This method turns the sets resembling a DNF into {@link DataNF}
     *
     * @param set A set of sets resembling a DNF
     * @return A {@link DataNF}
     */
    private DataNF mapVars( HashSet<HashSet<AnswerTerm>> set ) {
        DataNF mappedVars = new DataNF();
        for ( HashSet<AnswerTerm> terms : set ) {
            HashSet<Variable> vars = new HashSet<>();
            HashSet<DataNF> nfs = new HashSet<>();
            HashSet<AnswerTerm> oths = new HashSet<>();
            for ( AnswerTerm term : terms ) {
                if ( term instanceof Variable ) {
                    vars.add( (Variable) term );
                } else if ( term instanceof DataNF ) {
                    nfs.add( (DataNF) term );
                } else {
                    oths.add( term );
                }
            }
            if ( !nfs.isEmpty() ) {
                //If there are multiple DataNFs, there could be two DataNFs with the same set of Vars. These have to be summarized.
                DataNF nfsSummarized = new DataNF();
                for ( DataNF nf : nfs ) {
                    if ( nfsSummarized.isEmpty() ) {
                        nfsSummarized = nf;
                    } else {
                        DataNF temp = new DataNF();
                        HashSet<Variable> tempVars = new HashSet<>();
                        HashSet<HashSet<AnswerTerm>> tempAtsSet = new HashSet<>();
                        for ( HashSet<Variable> vars1 : nfsSummarized.keySet() ) {
                            for ( HashSet<Variable> vars2 : nf.keySet() ) {
                                tempVars = new HashSet<>();
                                tempAtsSet = new HashSet<>();
                                for ( HashSet<AnswerTerm> ats1 : nfsSummarized.get( vars1 ) ) {
                                    for ( HashSet<AnswerTerm> ats2 : nf.get( vars2 ) ) {
                                        HashSet<AnswerTerm> tempAts = new HashSet<>();
                                        tempAts.addAll( ats1 );
                                        tempAts.addAll( ats2 );
                                        tempAtsSet.add( tempAts );
                                    }
                                }
                                if ( temp.containsKey( tempVars ) ) {
                                    temp.get( tempVars ).addAll( tempAtsSet );
                                } else {
                                    temp.put( tempVars, tempAtsSet );
                                }
                            }
                        }
                        nfsSummarized = temp;
                    }
                }

                /*
                 * If there already is a DataNF (created at a previous point in time) in the currently looked at
                 * conjunction  it will be updated and then added to the mappedVars. In order to update the DataNF all
                 * Variables and all AnswerTerms are added to each entry in the DataNF (due to the distributive
                 * properties of conjunctions and disjunctions).
                 * If there is no DataNF but the specific composition of Variables already exists in mappedVars, the
                 * corresponding Set of AnswerTerms is simply added to the Set of Set of AnswerTerms in that entry.
                 * If there is neither a DataNF nor an entry for the composition of Variables, a new entry will be
                 * generated by simply adding the Set of Vars as the Key and a Set of Set of AnswerTerms a the Value.
                 */
                DataNF temp = new DataNF();
                for ( HashSet<Variable> nfVar : nfsSummarized.keySet() ) {
                    HashSet<Variable> tempNfVar = new HashSet<>( nfVar );
                    HashSet<HashSet<AnswerTerm>> tempAnswerTerms = new HashSet<>( nfsSummarized.get( nfVar ) );
                    tempNfVar.addAll( vars );

                    for ( HashSet<AnswerTerm> ats : tempAnswerTerms ) {
                        ats.addAll( oths );
                    }
                    temp.put( tempNfVar, tempAnswerTerms );
                }
                for ( HashSet<Variable> k : temp.keySet() ) {
                    if ( mappedVars.containsKey( k ) ) {
                        mappedVars.get( k ).addAll( temp.get( k ) );
                    } else {
                        mappedVars.put(k,temp.get( k ));
                    }
                }

            } else if ( mappedVars.containsKey( vars ) ) {
                mappedVars.get( vars ).add( oths );
            } else {
                HashSet<HashSet<AnswerTerm>> temp = new HashSet<>();
                if ( oths.isEmpty() ) {
                    //TODO this answer set needs an actual query and time point
                    AnswerTerm at = new AnswerSet( null, -1, "top" );
                    oths.add( at );
                }
                temp.add( oths );
                mappedVars.put( vars, temp );
            }
        }
        return mappedVars;
    }
}
