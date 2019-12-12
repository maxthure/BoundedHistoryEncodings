package computing;

import answerTerms.AnswerTerm;
import queries.*;

public class DataPhi {

    private int pointInTime;
    private Query query;
    private AnswerTerm answerTerm;


    public DataPhi( int pointInTime, Query query, AnswerTerm answerTerm ) {
        this.pointInTime = pointInTime;
        this.query = query;
        this.answerTerm = answerTerm;
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
    public int hashCode() {
        return query.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if(obj instanceof DataPhi ){
            DataPhi phi = (DataPhi) obj;
            return phi.pointInTime == this.pointInTime && phi.query.equals( this.query );
        }
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return pointInTime + ": " + query + " = " + answerTerm;
    }

}
