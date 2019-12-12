package queries;

public class AtemporalQuery implements Query {

    String name;

    public AtemporalQuery( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals( AtemporalQuery atemporalQuery ) {
        return this.name.equals( atemporalQuery.name );
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals( Query query ) {
        if(query instanceof AtemporalQuery){
            return ( this.name.equals( ((AtemporalQuery) query).name ) );
        }
        return false;
    }
}
