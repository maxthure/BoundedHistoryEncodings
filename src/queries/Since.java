package queries;

public class Since implements Query {

    private Query subquery1;
    private Query subquery2;

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
}
