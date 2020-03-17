package queries;

/**
 * This class represents a weak previous in a query.
 */
public class WeakPrevious implements Query {

    Query subquery;

    public WeakPrevious( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "WeakPrevious(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof WeakPrevious){
            return ( this.subquery.equals( ((WeakPrevious) query).subquery ) );
        }
        return false;
    }
}
