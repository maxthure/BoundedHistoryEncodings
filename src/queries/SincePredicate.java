package queries;

/**
 * This class represents a since in a query.
 */
public class SincePredicate implements PredicateQuery {

    private final Query subquery1;
    private final Query subquery2;
    private final int p;

    public SincePredicate( Query subquery1, Query subquery2, int p ) {
        this.subquery1 = subquery1;
        this.subquery2 = subquery2;
        this.p = p;
    }

    public Query getSubquery1() {
        return subquery1;
    }

    public Query getSubquery2() {
        return subquery2;
    }

    public int getP() {
        return p;
    }

    @Override
    public String toString() {
        return "(" + subquery1 + " SPredicate" + p + " " + subquery2 + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof SincePredicate ) {
            SincePredicate q = (SincePredicate) query;
            return ( this.p == q.p && this.subquery1.equals( q.subquery1 ) && this.subquery2.equals( q.subquery2 ) );
        }
        if (this.p == 0){
            return this.subquery2.equals( query );
        }
        return false;
    }
}
