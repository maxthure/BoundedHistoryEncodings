package results;

import queries.*;

/**
 * This class represents a set of answers in an answer term.
 */
public class AnswerSet implements AnswerTerm {

    int pointInTime;
    Query query;
    String answer;

    public AnswerSet( Query query, int pointInTime ) {
        this.query = query;
        this.pointInTime = pointInTime;
        if ( query instanceof AtemporalQuery ) {
            answer = ( (AtemporalQuery) query ).getName();
        } else if ( query instanceof StrongPrevious ) {
            answer = "bottom";
        } else if ( query instanceof WeakPrevious ) {
            answer = "top";
        } else {
            answer = "invalid";
        }
    }

    public AnswerSet( Query query, int pointInTime, String answer ) {
        this.query = query;
        this.pointInTime = pointInTime;
        this.answer = answer;
    }

    public String getAnswer() {
        //TODO after testing change this back to simple return
        if (answer.equals( "bottom" ) || answer.equals( "top" ) || answer.equals( "invalid" ) || answer.startsWith( "SELECT * FROM result_table_" )){
            return answer;
        }
        return answer.replace( "autos", "autos"+pointInTime );
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return answer;
    }

    @Override
    public int hashCode() {
        return pointInTime;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof AnswerSet ) {
            AnswerSet aSet = (AnswerSet) obj;
            return this.query.equals( aSet.query );
        }
        return super.equals( obj );
    }
}
