package queries;

/**
 * This class represents a strong next in a query.
 */
public class StrongNextPredicate implements PredicateQuery {

    private final Query subquery;
    private final int p;

    public StrongNextPredicate( Query subquery, int p ) {
        this.subquery = subquery;
        this.p = p;
    }

    public Query getSubquery() {
        return subquery;
    }

    public int getP() {
        return p;
    }

    @Override
    public String toString() {
        return "StrongNextPredicate(" + p + ", "  + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof StrongNextPredicate ) {
            StrongNextPredicate q = (StrongNextPredicate) query;
            return ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        if (this.p == 0){
            return this.subquery.equals( query );
        }
        return false;
    }
}
