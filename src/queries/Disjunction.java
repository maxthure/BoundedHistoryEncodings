package queries;

/**
 * This class represents a disjunction in a query.
 */
public class Disjunction implements Query {

    private Query subquery1;
    private Query subquery2;

    public Disjunction( Query subquery1, Query subquery2 ) {
        this.subquery1 = subquery1;
        this.subquery2 = subquery2;
    }

    public Query getSubquery1() {
        return subquery1;
    }

    public Query getSubquery2() {
        return subquery2;
    }

    @Override
    public String toString() {
        return "(" + subquery1 + " u " + subquery2 + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof Disjunction){
            Disjunction disjunction = (Disjunction) query;
            return ( this.subquery1.equals( disjunction.subquery1 ) && this.subquery2.equals( disjunction.subquery2 ) ) || ( this.subquery1.equals( disjunction.subquery2 ) && this.subquery2.equals( disjunction.subquery1 ) );
        }
        return false;
    }
}
