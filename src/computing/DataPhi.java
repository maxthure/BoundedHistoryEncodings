package computing;

import answerTerms.AnswerTerm;
import queries.*;

public class Phi {

    private int pointInTime;
    private Query query;
    private AnswerTerm answerTerm;


    public Phi( int pointInTime, Query query ) {
        this.pointInTime = pointInTime;
        this.query = query;
        answerTerm = new Function().compute( pointInTime, query );
    }

    public int getPointInTime() {
        return pointInTime;
    }

    public Query getQuery() {
        return query;
    }

    public void setAnswerTerm( AnswerTerm answerTerm ) {
        this.answerTerm = answerTerm;
    }

    public AnswerTerm getAnswerTerm() {
        return answerTerm;
    }

    @Override
    public boolean equals( Object obj ) {
        if(obj instanceof Phi){
            Phi phi = (Phi) obj;
            return phi.pointInTime == this.pointInTime && phi.query.equals( this.query );
        }
        return super.equals( obj );
    }

    @Override
    public int hashCode() {
        return query.hashCode();
    }

    @Override
    public String toString() {
        return pointInTime + ": " + query + " = " + answerTerm;
    }

}
