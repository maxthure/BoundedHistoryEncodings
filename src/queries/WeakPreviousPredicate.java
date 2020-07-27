package queries;

/**
 * This class represents a weak previous in a query.
 */
public class WeakPreviousPredicate implements PredicateQuery {

    private final Query subquery;
    private final int p;

    public WeakPreviousPredicate( Query subquery, int p ) {
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
        return "WeakPreviousPredicate(" + p + ", "  + subquery + ")";
    }
  @Override
    public boolean equals( Query query ) {
        boolean temp = false;
        if (this.p == 0){
            temp = this.subquery.equals( query );
        }
        if ( query instanceof WeakPreviousPredicate && !temp ) {
            WeakPreviousPredicate q = (WeakPreviousPredicate) query;
            temp = ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        return temp;
    }
}
