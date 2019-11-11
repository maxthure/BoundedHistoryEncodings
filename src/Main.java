import computing.*;
import queries.*;

public class Main {

    public static void main( String[] args ) {
        //Query query = new StrongPrevious( new Conjunction( new StrongNext( new StrongPrevious( new AtemporalQuery( "a" ) ) ), new AtemporalQuery( "b" ) ) );
        Query query = new Since( new AtemporalQuery( "a" ), new StrongNext( new Conjunction( new AtemporalQuery( "b" ), new StrongNext( new StrongNext( new AtemporalQuery( "c" ) ) ) ) ) );
        //Query query = new StrongPrevious(new StrongNext(new StrongPrevious(new StrongNext(new AtemporalQuery("a")))));
        //Query query = new StrongPrevious( new Until( new StrongPrevious( new AtemporalQuery( "a" ) ), new AtemporalQuery( "b" ) ) );

        Phi phi = new Phi();

        /*
        Term term = phi.compute( 3, query );
        System.out.println( term );
        NormalForm normalForm = new NormalForm();
        Term term1 = normalForm.compute( term );
        System.out.println( term1 );


        long beginning;
        int i = 0;

        for (int j = 0; j < 10; j++){
            Term term = phi.compute( i, query );
            NormalForm normalForm = new NormalForm();
            Term term1 = normalForm.compute( term );
            System.out.println( term1 );
        }

        beginning = System.currentTimeMillis();
        while ( System.currentTimeMillis() - beginning < 200 ){
            Term term = phi.compute( i, query );
            NormalForm normalForm = new NormalForm();
            Term term1 = normalForm.compute( term );
            System.out.println( term1 );
            i++;
        }
  */

        //Term term2 = new TermDisjunction( new TermConjunction( new AnswerTerm( new AtemporalQuery( "a" ), 1 ), new AnswerTerm( new AtemporalQuery( "a" ), 2 )), new TermConjunction( new AnswerTerm( new AtemporalQuery( "a" ), 3 ), new AnswerTerm( new AtemporalQuery( "a" ), 4 ) ) );
        Term term2 = new TermConjunction( new TermConjunction( new TermDisjunction( new AnswerTerm( new AtemporalQuery( "a" ), 1 ), new AnswerTerm( new AtemporalQuery( "a" ), 3 ) ), new TermDisjunction( new AnswerTerm( new AtemporalQuery( "a" ), 1 ), new AnswerTerm( new AtemporalQuery( "a" ), 4 ) ) ), new TermConjunction( new TermDisjunction( new AnswerTerm( new AtemporalQuery( "a" ), 2 ), new AnswerTerm( new AtemporalQuery( "a" ), 3 ) ), new TermDisjunction( new AnswerTerm( new AtemporalQuery( "a" ), 2 ), new AnswerTerm( new AtemporalQuery( "a" ), 4 ) ) ) );
        NormalForm nF = new NormalForm();
        System.out.println( nF.normalizeKNF( term2 ) );
        System.out.println( nF.normalizeDNF( term2 ) );
    }
}
