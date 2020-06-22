package queries;

/**
 * This class represents a some time in the past in a query.
 */
public class EventuallyPastPredicate implements PredicateQuery{

    private final Query subquery;
    private final int p;

    public EventuallyPastPredicate( Query subquery, int p ) {
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
        return "EventuallyPastPredicate(" + p + ", "  + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof EventuallyPastPredicate ) {
            EventuallyPastPredicate q = (EventuallyPastPredicate) query;
            return ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        if (this.p == 0){
            return this.subquery.equals( query );
        }
        return false;
    }
}
