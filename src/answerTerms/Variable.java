package answerTerms;

import answerTerms.AnswerTerm;
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
        return "\u001B[34m" + "Variable<" + pointInTime + ", " + query + ">" + "\u001B[0m";
        //return "Variable<" + pointInTime + ", " + query + ">";
    }

    @Override
    public int hashCode() {
        return pointInTime;
    }

    @Override
    public boolean equals( Object obj ) {
        if(obj instanceof Variable){
            Variable var = (Variable) obj;
            return this.query.equals( var.query );
        }
        return super.equals( obj );
    }
}
