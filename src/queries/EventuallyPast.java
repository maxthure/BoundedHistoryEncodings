package queries;

/**
 * This class represents a some time in the past in a query.
 */
public class EventuallyPast implements Query{

    private final Query subquery;

    public EventuallyPast( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "EventuallyPast(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof PredicateQuery ) {
            return query.equals( this );
        } else {
            if ( query instanceof EventuallyPast ) {
                return ( this.subquery.equals( ( (EventuallyPast) query ).subquery ) );
            }
        }
        return false;
    }
}
