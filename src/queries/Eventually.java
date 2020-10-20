package queries;

/**
 * This class represents an eventually in a query.
 */
public class Eventually implements Query {

    private final Query subquery;

    public Eventually( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "Eventually(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof Eventually ) {
            return ( this.subquery.equals( ( (Eventually) query ).subquery ) );
        }
        return false;
    }
}
