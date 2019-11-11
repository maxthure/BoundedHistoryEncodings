package computing;

import queries.AtemporalQuery;

public class Function {

    protected Term conjunction( Term term1, Term term2 ) {
        if ( term1.toString().equals( term2.toString() ) ) {
            return term1;
        }
        if ( term1 instanceof AnswerTerm && term2 instanceof AnswerTerm ) {
            AnswerTerm aT1 = (AnswerTerm) term1;
            AnswerTerm aT2 = (AnswerTerm) term2;
            //return new AnswerTerm(new Conjunction(aT1.getQuery(),aT2.getQuery()));
            if ( aT1.answer.contains( aT2.answer ) ) {
                return new AnswerTerm( new AtemporalQuery( aT1.answer ), -1 );
            }
            if ( aT2.answer.contains( aT1.answer ) ) {
                return new AnswerTerm( new AtemporalQuery( aT2.answer ), -1 );
            }
            return new AnswerTerm( new AtemporalQuery( "(" + aT1.answer + " n " + aT2.answer + ")" ), -1 );
        }
        return new TermConjunction( term1, term2 );
    }

    protected Term disjunction( Term term1, Term term2 ) {
        if ( term1.toString().equals( term2.toString() ) ) {
            return term1;
        }
        if ( term1 instanceof AnswerTerm && term2 instanceof AnswerTerm ) {
            AnswerTerm aT1 = (AnswerTerm) term1;
            AnswerTerm aT2 = (AnswerTerm) term2;
            //return new AnswerTerm(new Disjunction(aT1.getQuery(),aT2.getQuery()));
            if ( aT1.answer.contains( aT2.answer ) ) {
                return new AnswerTerm( new AtemporalQuery( aT1.answer ), -1 );
            }
            if ( aT2.answer.contains( aT1.answer ) ) {
                return new AnswerTerm( new AtemporalQuery( aT2.answer ), -1 );
            }
            return new AnswerTerm( new AtemporalQuery( "(" + aT1.answer + " u " + aT2.answer + ")" ), -1 );
        }
        return new TermDisjunction( term1, term2 );
    }

}
