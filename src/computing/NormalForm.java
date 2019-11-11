package computing;

public class NormalForm extends Function {

    public Term compute( Term term ) {
        Term term1 = normalizeDNF( term );
        return simplify( term1 );
    }

    //TODO reevaluate
    /*
    private Term normalize( Term term ) {
        if ( term instanceof TermDisjunction ) {
            TermDisjunction tD = (TermDisjunction) term;
            return disjunction( normalize( tD.term1 ), normalize( tD.term2 ) );
        }
        if ( term instanceof TermConjunction ) {
            TermConjunction tC = (TermConjunction) term;
            if ( tC.term1 instanceof TermDisjunction ) {
                TermDisjunction subtD = (TermDisjunction) tC.term1;
                return disjunction( conjunction( normalize( subtD.term1 ), normalize( tC.term2 ) ), conjunction( normalize( subtD.term2 ), normalize( tC.term2 ) ) );
            }
            if ( tC.term1 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term1;
                return conjunction( normalize( subtC ), normalize( tC.term2 ) );
            }
            if ( tC.term2 instanceof TermDisjunction ) {
                TermDisjunction subtD = (TermDisjunction) tC.term2;
                return disjunction( conjunction( normalize( tC.term1 ), normalize( subtD.term1 ) ), conjunction( normalize( tC.term1 ), normalize( subtD.term2 ) ) );
            }
            if ( tC.term2 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term2;
                return conjunction( normalize( tC.term1 ), normalize( subtC ) );
            }
        }
        return term;
    }
*/
    public Term normalizeDNF( Term term ) {
        if ( term instanceof TermDisjunction ) {
            TermDisjunction tD = (TermDisjunction) term;
            return disjunction( normalizeDNF( tD.term1 ), normalizeDNF( tD.term2 ) );
        }
        if ( term instanceof TermConjunction ) {
            TermConjunction tC = (TermConjunction) term;
            if ( tC.term1 instanceof TermDisjunction ) {
                TermDisjunction subtC = (TermDisjunction) tC.term1;
                return disjunction( normalizeDNF( conjunction( subtC.term1, tC.term2 ) ), normalizeDNF( conjunction( subtC.term2, tC.term2 ) ) );
            }
            //if ( tC.term2 instanceof TermDisjunction ) {
             //   TermDisjunction subtC = (TermDisjunction) tC.term2;
               // return disjunction( normalizeDNF( conjunction( tC.term1, subtC.term1 ) ), normalizeDNF( conjunction( tC.term1, subtC.term2 ) ) );
            //}
            if ( tC.term1 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term1;
                return normalizeDNF( conjunction( subtC.term1 , conjunction( subtC.term2, tC.term2 ) ) );
            }
            if ( tC.term1 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term1;
                return normalizeDNF( conjunction( subtC.term1 , conjunction( subtC.term2, tC.term2 ) ) );
            }

        }
        return term;
    }

    public Term normalizeKNF( Term term ) {
        if ( term instanceof TermConjunction ) {
            TermConjunction tD = (TermConjunction) term;
            return conjunction( normalizeKNF( tD.term1 ), normalizeKNF( tD.term2 ) );
        }
        if ( term instanceof TermDisjunction ) {
            TermDisjunction tC = (TermDisjunction) term;
            if ( tC.term1 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term1;
                return conjunction( normalizeKNF( disjunction( subtC.term1, tC.term2 ) ), normalizeKNF( disjunction( subtC.term2, tC.term2 ) ) );
            }
            if ( tC.term2 instanceof TermConjunction ) {
                TermConjunction subtC = (TermConjunction) tC.term2;
                return conjunction( normalizeKNF( disjunction( tC.term1, subtC.term1 ) ), normalizeKNF( disjunction( tC.term1, subtC.term2 ) ) );
            }
        }
        return term;
    }

    private Term simplify( Term term ) {
        if ( term instanceof TermConjunction ) {
            TermConjunction tC = (TermConjunction) term;
            if ( tC.term1 instanceof AnswerTerm && tC.term2 instanceof TermConjunction && ( (TermConjunction) tC.term2 ).term1 instanceof AnswerTerm ) {
                return simplify( conjunction( conjunction( tC.term1, ( (TermConjunction) tC.term2 ).term1 ), ( (TermConjunction) tC.term2 ).term2 ) );
            }
            return conjunction( simplify( tC.term1 ), simplify( tC.term2 ) );
        }
        if ( term instanceof TermDisjunction ) {
            TermDisjunction tD = (TermDisjunction) term;
            if ( tD.term1 instanceof AnswerTerm && tD.term2 instanceof TermDisjunction && ( (TermDisjunction) tD.term2 ).term1 instanceof AnswerTerm ) {
                return simplify( disjunction( disjunction( tD.term1, ( (TermDisjunction) tD.term2 ).term1 ), ( (TermDisjunction) tD.term2 ).term2 ) );
            }
            return disjunction( simplify( tD.term1 ), simplify( tD.term2 ) );
        }
        return term;
    }

}
