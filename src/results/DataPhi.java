package results;

import queries.*;


/**
 * This class represents an answer to a given query at a specific point in time.
 * It contains:
 * <li>the time in point
 * <li>the actual {@link Query}
 * <li>the {@link AnswerTerm} in DNF
 * <li>the {@code AnswerTerm} in {@link DataNF}
 */
public class DataPhi {

    private int pointInTime;
    private Query query;
    private AnswerTerm answerTerm;
    private DataNF dataNF;

    public DataPhi( int pointInTime, Query query, AnswerTerm answerTerm, DataNF dataNF ) {
        this.pointInTime = pointInTime;
        this.query = query;
        this.answerTerm = answerTerm;
        this.dataNF = dataNF;
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

    public DataNF getDataNF() {
        return dataNF;
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
