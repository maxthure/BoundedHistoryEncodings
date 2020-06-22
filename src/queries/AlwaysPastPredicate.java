package queries;

/**
 * This class represents an always in the past in a query.
 */
public class AlwaysPastPredicate implements PredicateQuery{

    private final Query subquery;
    private final int p;

    public AlwaysPastPredicate( Query subquery, int p ) {
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
        return "AlwaysPastPredicate(" + p + ", "  + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof AlwaysPastPredicate ) {
            AlwaysPastPredicate q = (AlwaysPastPredicate) query;
            return ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        if (this.p == 0){
            return this.subquery.equals( query );
        }
        return false;
    }
}
