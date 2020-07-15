package results;

import queries.*;

/**
 * This class represents a set of answers in an answer term.
 */
public class FilterAnswerSet implements AnswerTerm {

    int pointInTime;
    Filter query;
    DataNF answer;

    public FilterAnswerSet( Filter query, int pointInTime, DataNF answer ) {
        this.query = query;
        this.pointInTime = pointInTime;
        this.answer = answer;
    }

    public DataNF getAnswer() {
        return answer;
    }

    public Filter getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return  ( (Filter) query ).getFilter().replace( "phi", "("+answer+")" );
    }

    @Override
    public int hashCode() {
        return pointInTime;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof FilterAnswerSet ) {
            FilterAnswerSet aSet = (FilterAnswerSet) obj;
            return this.query.equals( aSet.query );
        }
        return super.equals( obj );
    }
}
