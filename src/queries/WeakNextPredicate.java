package queries;

/**
 * This class represents a weak next in a query.
 */
public class WeakNextPredicate implements PredicateQuery {

    private final Query subquery;
    private final int p;

    public WeakNextPredicate( Query subquery, int p ) {
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
        return "WeakNextPredicate(" + p + ", "  + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        boolean temp = false;
        if (this.p == 0){
            temp = this.subquery.equals( query );
        }
        if ( query instanceof WeakNextPredicate && !temp ) {
            WeakNextPredicate q = (WeakNextPredicate) query;
            temp = ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        return temp;
    }
}
