package queries;

/**
 * This class represents a since in a query.
 */
public class Since implements Query {

    private final Query subquery1;
    private final Query subquery2;

    public Since( Query subquery1, Query subquery2 ) {
        this.subquery1 = subquery1;
        this.subquery2 = subquery2;
    }

    public Query getSubquery1() {
        return subquery1;
    }

    public Query getSubquery2() {
        return subquery2;
    }

    @Override
    public String toString() {
        return "(" + subquery1 + " S " + subquery2 + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof Since ) {
            return ( this.subquery1.equals( ( (Since) query ).subquery1 ) && this.subquery2.equals( ( (Since) query ).subquery2 ) );
        }
        return false;
    }
}
