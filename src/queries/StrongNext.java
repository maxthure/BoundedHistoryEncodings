package queries;

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
}
