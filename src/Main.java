import results.DataPhi;
import computing.*;
import queries.*;

public class Main {

    public static void main( String[] args ) {

        SubquerySaver subquerySaver = new SubquerySaver();
        FunctionPhi functionPhi = new FunctionPhi( subquerySaver );
        Eval eval = new Eval();

        //Query query = new StrongPrevious( new Conjunction( new StrongNext( new StrongPrevious( new AtemporalQuery( "a" ) ) ), new AtemporalQuery( "b" ) ) );
        //Query query = new Since( new AtemporalQuery( "a" ), new StrongNext( new AtemporalQuery( "b" ) ) );
        Query query = new Conjunction( new StrongPrevious( new StrongPrevious( new AtemporalQuery( "SELECT * FROM table1" ) ) ), new Since( new AtemporalQuery( "SELECT * FROM table1" ), new StrongNext( new AtemporalQuery( "SELECT * FROM table2" ) ) ) );
        //Query query = new Disjunction( new Disjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ) ), new Conjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new StrongPrevious( new Until( new StrongPrevious( new AtemporalQuery( "a" ) ), new AtemporalQuery( "b" ) ) );

        //Query query = new Since( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongNext( new Conjunction( new AtemporalQuery( "SELECT * FROM Table2" ), new StrongNext( new StrongNext( new AtemporalQuery( "SELECT * FROM Table3" ) ) ) ) ) );


        for ( int i = 0; i < 5; i++ ) {
            DataPhi phi = functionPhi.compute( i, query );
            //TODO Bis hier sollte alles korrekt sein
            subquerySaver.saveSubqueries( phi );
            //System.out.println( phi );
            //AnswerTerm result = phi.getAnswerTerm();
            //System.out.println( "Result: " +result );
            //eval.eval( result );
        }
        for ( DataPhi phi : subquerySaver.getSavedSubqueries() ) {
            //System.out.println( phi.getAnswerTerm() );
        }

/*
        AnswerTerm term1 = new AnswerTermDisjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 1 ), new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 2 ) ), new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 3 ), new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 4 ) ) );
        AnswerTerm term2 = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 3 ) ), new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 4 ) ) ), new AnswerTermConjunction( new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 3 ) ), new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 4 ) ) ) );

        AnswerTerm result = normalForm.prepare( term1 );
        eval.eval( result );
*/
    }

}
