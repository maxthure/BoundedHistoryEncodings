package queries;

/**
 * This class represents a weak next in a query.
 */
public class WeakNext implements Query {

    private final Query subquery;

    public WeakNext( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "WeakNext(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof PredicateQuery ) {
            return query.equals( this );
        } else {
            if ( query instanceof WeakNext ) {
                return ( this.subquery.equals( ( (WeakNext) query ).subquery ) );
            }
        }
        return false;
    }
}
