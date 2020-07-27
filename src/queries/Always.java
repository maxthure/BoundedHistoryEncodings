package queries;

/**
 * This class represents an always in a query.
 */
public class Always implements Query {

    private final Query subquery;

    public Always( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "Always(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof PredicateQuery ) {
            return query.equals( this );
        } else {
            if ( query instanceof Always ) {
                return ( this.subquery.equals( ( (Always) query ).subquery ) );
            }
        }
        return false;
    }
}
