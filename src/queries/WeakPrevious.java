package queries;

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
}
