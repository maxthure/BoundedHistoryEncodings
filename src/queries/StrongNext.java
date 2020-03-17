package queries;

/**
 * This class represents a strong next in a query.
 */
public class StrongNext implements Query {

    Query subquery;

    public StrongNext( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "StrongNext(" + subquery + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof StrongNext){
            return ( this.subquery.equals( ((StrongNext) query).subquery ) );
        }
        return false;
    }
}
