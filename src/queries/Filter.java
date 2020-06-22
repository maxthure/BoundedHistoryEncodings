package queries;

/**
 * @author Thure Nebendahl on 22.06.20
 */
public class Filter implements Query {

    private final Query subquery;
    private final String filter;

    public Filter( String filter, Query subquery ) {
        this.filter = filter;
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return "Filter(" + filter + ", " + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof Filter ) {
            return ( this.filter.equals( ( (Filter) query ).filter ) && this.subquery.equals( ( (Filter) query ).subquery ) );
        }
        return false;
    }
}
