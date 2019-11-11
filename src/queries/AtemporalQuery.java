package queries;

public class AtemporalQuery implements Query {

    String name;

    public AtemporalQuery( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
