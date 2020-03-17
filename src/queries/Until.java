package queries;

/**
 * This class represents an until in a query.
 */
public class Until implements Query {

    private final Query subquery1;
    private final Query subquery2;

    public Until(Query subquery1, Query subquery2){
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
        return "(" + subquery1 + " U " + subquery2 + ")";
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof Until){
            return ( this.subquery1.equals( ((Until) query).subquery1 ) && this.subquery2.equals( ((Until) query).subquery2 ) );
        }
        return false;
    }
}
