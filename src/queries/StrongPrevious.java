package queries;

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
}
