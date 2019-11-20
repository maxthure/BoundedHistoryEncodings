package computing;

import queries.Query;

public class Variable implements AnswerTerm {

    int pointInTime;
    Query query;

    public Variable( int pointInTime, Query query ) {
        this.pointInTime = pointInTime;
        this.query = query;
    }

    public int getPointInTime() {
        return pointInTime;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "\u001B[34m" + "Variable[" + pointInTime + ", " + query + "]" + "\u001B[0m";
    }
}
