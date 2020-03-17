package queries;

/**
 * This class represents a conjunction in a query.
 */
public class Conjunction implements Query {

    private final Query subquery1;
    private final Query subquery2;

    public Conjunction( Query subquery1, Query subquery2 ) {
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
        return "(" + subquery1 + " n " + subquery2 + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof Conjunction){
            Conjunction conjunction = (Conjunction) query;
            return ( this.subquery1.equals( conjunction.subquery1 ) && this.subquery2.equals( conjunction.subquery2 ) ) || ( this.subquery1.equals( conjunction.subquery2 ) && this.subquery2.equals( conjunction.subquery1 ) );
        }
        return false;
    }
}
