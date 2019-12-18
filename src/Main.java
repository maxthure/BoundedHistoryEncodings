import answerTerms.*;
import computing.DataPhi;
import computing.*;
import queries.*;

public class Main {

    public static void main( String[] args ) {

        SubquerySaver subquerySaver = new SubquerySaver();
        FunctionPhi functionPhi = new FunctionPhi( subquerySaver );
        NormalForm normalForm = new NormalForm();
        Eval eval = new Eval();

        //Query query = new StrongPrevious( new Conjunction( new StrongNext( new StrongPrevious( new AtemporalQuery( "a" ) ) ), new AtemporalQuery( "b" ) ) );
        //Query query = new Since( new AtemporalQuery( "a" ), new StrongNext( new AtemporalQuery( "b" ) ) );
        Query query = new Conjunction( new StrongPrevious( new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table1" ) ) ), new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new Disjunction( new Disjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ) ), new Conjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new StrongPrevious( new Until( new StrongPrevious( new AtemporalQuery( "a" ) ), new AtemporalQuery( "b" ) ) );

        //Query query = new Since( new AtemporalQuery( "a" ), new StrongNext( new Conjunction( new AtemporalQuery( "b" ), new StrongNext( new StrongNext( new AtemporalQuery( "c" ) ) ) ) ) );


        for ( int i = 0; i < 1; i++ ) {
            DataPhi phi = functionPhi.compute( i, query );
            subquerySaver.saveSubqueries( phi );
        }
        for ( DataPhi phi : subquerySaver.getSavedSubqueries() ) {
            System.out.println( phi );
            AnswerTerm result = normalForm.prepare( phi.getAnswerTerm() );
            System.out.println( result );
            eval.eval( result );
        }

/*
        AnswerTerm term1 = new AnswerTermDisjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 1 ), new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 2 ) ), new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 3 ), new AnswerSet( new AtemporalQuery( "SELECT * FROM Table" ), 4 ) ) );
        AnswerTerm term2 = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 3 ) ), new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 4 ) ) ), new AnswerTermConjunction( new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 3 ) ), new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "a" ), 1 ), new AnswerSet( new AtemporalQuery( "a" ), 4 ) ) ) );

        AnswerTerm result = normalForm.prepare( term1 );
        eval.eval( result );
*/
    }

}
