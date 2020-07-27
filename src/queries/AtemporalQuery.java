package queries;

/**
 * This class represents an atemporal query in a query.
 */
public class AtemporalQuery implements Query {

    private final String name;

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

    @Override
    public boolean equals( Query query ) {
        if ( query instanceof PredicateQuery ) {
            return query.equals( this );
        } else {
            if ( query instanceof AtemporalQuery ) {
                return ( this.name.equals( ( (AtemporalQuery) query ).name ) );
            }
        }
        return false;
    }
}
