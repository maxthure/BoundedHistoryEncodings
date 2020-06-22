package queries;

/**
 * This class represents an eventually in a query.
 */
public class EventuallyPredicate implements PredicateQuery{

    private final Query subquery;
    private final int p;

    public EventuallyPredicate( Query subquery, int p ) {
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
        return "EventuallyPredicate(" + p + ", "  + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof EventuallyPredicate ) {
            EventuallyPredicate q = (EventuallyPredicate) query;
            return ( this.p == q.p && this.subquery.equals( q.subquery ) );
        }
        if (this.p == 0){
            return this.subquery.equals( query );
        }
        return false;
    }
}
