package queries;

public class WeakNext implements Query {

    Query subquery;

    public WeakNext( Query subquery ) {
        this.subquery = subquery;
    }

    public Query getSubquery() {
        return subquery;
    }

    @Override
    public String toString() {
        return "WeakNext(" + subquery + ")";
    }
}
