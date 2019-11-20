package computing;

import queries.*;

public class AnswerSet implements AnswerTerm {

    int pointInTime;
    Query query;
    String answer;

    public AnswerSet( Query query, int pointInTime ) {
        this.query = query;
        this.pointInTime = pointInTime;
        if ( query instanceof AtemporalQuery ) {
            answer = ( (AtemporalQuery) query ).getName(); //.toUpperCase();
            if(pointInTime >= 0){
                answer = answer+pointInTime;
            }
        } else if ( query instanceof StrongPrevious ) {
            answer = "empty set";
        } else if ( query instanceof WeakPrevious ) {
            answer = "delta Nv";
        } else {
            answer = "invalid";
        }
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return answer;
    }
}
