import results.*;
import computing.*;
import queries.*;

public class Main {

    public static void main( String[] args ) {

        SubquerySaver subquerySaver = new SubquerySaver();
        FunctionPhi functionPhi = new FunctionPhi( subquerySaver );
        Eval eval = new Eval();

        /**
         * At this point some example Queries are generated that are mainly used for quick testing while developing.
         * These examples should be replaced by real queries. These real queries may be generated by processing real input.
         */
        //Query query = new StrongPrevious( new StrongNext( new AtemporalQuery( "A" ) ) );
        //Query query = new StrongPrevious( new Conjunction( new StrongNext( new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table1" ) ) ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new Since( new AtemporalQuery( "a" ), new StrongNext( new AtemporalQuery( "b" ) ) );
        //Query query = new Conjunction( new StrongPrevious( new StrongPrevious( new AtemporalQuery( "SELECT * FROM table1" ) ) ), new Since( new AtemporalQuery( "SELECT * FROM table1" ), new StrongNext( new AtemporalQuery( "SELECT * FROM table2" ) ) ) );
        //Query query = new Disjunction( new Disjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ) ), new Conjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new StrongPrevious( new Until( new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table1" ) ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );

        Query query = new Since( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongNext( new Conjunction( new AtemporalQuery( "SELECT * FROM Table2" ), new StrongNext( new StrongNext( new AtemporalQuery( "SELECT * FROM Table3" ) ) ) ) ) );

        /**
         * This part basically does all the work at the moment:
         * - calculating PhiI
         * - saving the subqueries
         * - evaluating the result (AnswerTerm of PhiI)
         */
        for ( int i = 0; i < 5; i++ ) {
            DataPhi phi = functionPhi.compute( i, query );
            subquerySaver.saveSubqueries( phi );
            //TODO Bis hier sollte alles korrekt sein
            //System.out.println( phi );
            AnswerTerm result = phi.getDataNF();
            //System.out.println( "Result: " +result );
            //eval.eval( result );
        }
        for ( DataPhi phi : subquerySaver.getSavedSubqueries() ) {
            //System.out.println( phi );
        }

/*
        AnswerTerm answerTerm = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "A" ), 0 ), new AnswerTermDisjunction( new AnswerTermConjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerTermDisjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerSet( new AtemporalQuery( "B" ), 0 ) ) ), new AnswerSet( new AtemporalQuery( "C" ), 0 ) ) ),
                new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "D" ), 0 ), new Variable( 0, new AtemporalQuery( "Y" ) ) ) );
        NormalForm normalForm = new NormalForm();
        AnswerTerm answerTerm1 = normalForm.prepareNF( answerTerm );
        AnswerTerm answerTerm2 = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "A" ), 0 ), new AnswerTermDisjunction( new AnswerTermConjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerTermDisjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerSet( new AtemporalQuery( "B" ), 0 ) ) ), new AnswerSet( new AtemporalQuery( "C" ), 0 ) ) ),
                new AnswerTermDisjunction( answerTerm1, new Variable( 0, new AtemporalQuery( "Y" ) ) ) );
        normalForm.prepareNF( answerTerm2 );
  */  }

}
