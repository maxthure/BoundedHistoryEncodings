package results;

import queries.*;

public class AnswerSet implements AnswerTerm {

    int pointInTime;
    Query query;
    String answer;

    public AnswerSet( Query query, int pointInTime ) {
        this.query = query;
        this.pointInTime = pointInTime;
        if ( query instanceof AtemporalQuery ) {
            answer = ( (AtemporalQuery) query ).getName();
            if(pointInTime >= 0){
                answer = answer;
            }
        } else if ( query instanceof StrongPrevious ) {
            answer = "empty set";
        } else if ( query instanceof WeakPrevious ) {
            answer = "delta Nv";
        } else {
            answer = "invalid";
        }
    }

    public String getAnswer() {
        return answer;
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
        if(obj instanceof AnswerSet){
            AnswerSet aSet = (AnswerSet) obj;
            return this.query.equals( aSet.query );
        }
        return super.equals( obj );
    }
}
