package queries;

/**
 * This class represents a strong previous in a query.
 */
public class StrongPrevious implements Query {

    Query subquery;

    public StrongPrevious( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "StrongPrevious(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof StrongPrevious){
            return ( this.subquery.equals( ((StrongPrevious) query).subquery ) );
        }
        return false;
    }
}
