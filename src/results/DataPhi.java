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
    private DataNF dataNF;
    private boolean evaluated;

    //For a version that includes the AnswerTerm in DataPhi:
    private AnswerTerm answerTerm;


    public DataPhi( int pointInTime, Query query, DataNF dataNF ) {
        this.pointInTime = pointInTime;
        this.query = query;
        this.dataNF = dataNF;
    }


    //For a version that includes the AnswerTerm in DataPhi:

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


    //For a version that includes the AnswerTerm in DataPhi:

    public AnswerTerm getAnswerTerm() {
        return answerTerm;
    }

    public void setAnswerTerm( AnswerTerm answerTerm ) {
        this.answerTerm = answerTerm;
    }

    public DataNF getDataNF() {
        return dataNF;
    }

    public void setDataNF( DataNF dataNF ) {
        this.dataNF = dataNF;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated( boolean evaluated ) {
        this.evaluated = evaluated;
    }

    @Override
    public int hashCode() {
        return query.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof DataPhi ) {
            DataPhi phi = (DataPhi) obj;
            return phi.pointInTime == this.pointInTime && phi.query.equals( this.query );
        }
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return pointInTime + ": " + query + " = " + dataNF;
    }

}
