package queries;

/**
 * This class represents an always in the past in a query.
 */
public class AlwaysPast implements Query {

    private final Query subquery;

    public AlwaysPast( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "AlwaysPast(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof AlwaysPast ) {
            return ( this.subquery.equals( ( (AlwaysPast) query ).subquery ) );
        }
        return false;
    }
}
