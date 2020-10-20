import computing.*;
import queries.*;
import results.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main( String[] args ) throws IOException {

        SubquerySaver subquerySaver = new SubquerySaver();
        FunctionPhi functionPhi = new FunctionPhi( subquerySaver );
        Eval eval = new Eval();

        Query query1 = new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 );

        Query query2 = new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query3 = new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 );

        Query query4 = new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query5 = new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query6 = new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 );

        Query query7 = new Eventually ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query8 = new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query9 = new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query10 = new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 7 );


        Query query11 = new StrongNext ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) );

        Query query12 = new AlwaysPredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 7 );

        Query query13 = new Disjunction ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query14 = new WeakPreviousPredicate ( new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ), 6 );

        Query query15 = new WeakPrevious ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) );

        Query query16 = new WeakNextPredicate ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 5 );

        Query query17 = new Conjunction ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) );

        Query query18 = new StrongPreviousPredicate ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 0 );

        Query query19 = new WeakPrevious ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ) );

        Query query20 = new EventuallyPastPredicate ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 0 );


        Query query21 = new EventuallyPredicate ( new Eventually ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 2 ) ), 3 );

        Query query22 = new StrongPrevious ( new EventuallyPredicate ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ), 2 ) );

        Query query23 = new Conjunction ( new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 1 ), new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ) );

        Query query24 = new SincePredicate ( new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 8 );

        Query query25 = new AlwaysPastPredicate ( new WeakNextPredicate ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 2 ), 7 );

        Query query26 = new WeakPreviousPredicate ( new WeakNext ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 3 );

        Query query27 = new Eventually ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) );

        Query query28 = new StrongPrevious ( new WeakNextPredicate ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 0 ) );

        Query query29 = new StrongPrevious ( new StrongPreviousPredicate ( new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), 9 ) );

        Query query30 = new SincePredicate ( new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ), 0 );


        Query query31 = new AlwaysPast ( new Always ( new StrongPrevious ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) );

        Query query32 = new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AlwaysPredicate ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 1 ) ) );

        Query query33 = new StrongPrevious ( new AlwaysPastPredicate ( new WeakNextPredicate ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 9 ), 4 ) );

        Query query34 = new Eventually ( new Until ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new Eventually ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) );

        Query query35 = new Conjunction ( new Conjunction ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ) );

        Query query36 = new EventuallyPredicate ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new SincePredicate ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), 5 ), 9 );

        Query query37 = new Conjunction ( new Disjunction ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) );

        Query query38 = new AlwaysPast ( new Eventually ( new StrongNextPredicate ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 0 ) ) );

        Query query39 = new StrongNext ( new EventuallyPastPredicate ( new StrongNextPredicate ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 1 ), 2 ) );

        Query query40 = new StrongPrevious ( new StrongNext ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 7 ), 5 ) ) );


        Query query41 = new Disjunction ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new StrongNextPredicate ( new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ), 2 ), 2 ), new Eventually ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) );

        Query query42 = new StrongNext ( new WeakPreviousPredicate ( new StrongNext ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 5 ) ), 9 ) );

        Query query43 = new StrongNext ( new WeakPrevious ( new WeakNextPredicate ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ), 4 ) ) );

        Query query44 = new Disjunction ( new Always ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new AlwaysPastPredicate ( new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ) );

        Query query45 = new StrongNext ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Always ( new StrongPrevious ( new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ) ) ), 3 ) );

        Query query46 = new SincePredicate ( new SincePredicate ( new StrongPrevious ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ) ), new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 2 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 );

        Query query47 = new WeakPrevious ( new WeakNextPredicate ( new AlwaysPast ( new EventuallyPastPredicate ( new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 5 ) ), 4 ) );

        Query query48 = new WeakPreviousPredicate ( new WeakPrevious ( new StrongNextPredicate ( new Since ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ) ), 3 );

        Query query49 = new WeakNext ( new EventuallyPast ( new AlwaysPastPredicate ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 2 ) ) );

        Query query50 = new StrongPrevious ( new StrongNextPredicate ( new StrongNext ( new StrongNextPredicate ( new EventuallyPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 9 ) ), 2 ) );


        Query query51 = new UntilPredicate ( new EventuallyPastPredicate ( new AlwaysPredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 7 ), 3 ), new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 1 );

        Query query52 = new StrongPreviousPredicate ( new Conjunction ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 7 ), new EventuallyPredicate ( new Disjunction ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 7 ) ), 9 );

        Query query53 = new WeakPreviousPredicate ( new StrongNextPredicate ( new Always ( new SincePredicate ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ), new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), 2 ) ), 5 ), 0 );

        Query query54 = new StrongNextPredicate ( new EventuallyPastPredicate ( new EventuallyPredicate ( new Since ( new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 4 ), 3 ), 5 );

        Query query55 = new Disjunction ( new StrongNext ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ) ), new StrongNextPredicate ( new AlwaysPredicate ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 3 ), 7 ) );

        Query query56 = new StrongNextPredicate ( new WeakPrevious ( new StrongNextPredicate ( new Until ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ), new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 3 ) ), 9 );

        Query query57 = new StrongPrevious ( new Eventually ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakPrevious ( new WeakNextPredicate ( new EventuallyPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 ) ) ) ) );

        Query query58 = new WeakPreviousPredicate ( new StrongPrevious ( new Until ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 1 ), new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Eventually ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) ), 1 );

        Query query59 = new StrongPreviousPredicate ( new Conjunction ( new EventuallyPastPredicate ( new AlwaysPredicate ( new WeakPrevious ( new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ) ), 5 ), 7 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 );

        Query query60 = new WeakPreviousPredicate ( new AlwaysPastPredicate ( new StrongNextPredicate ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakPreviousPredicate ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ) ), 4 ), 0 ), 4 );


        Query query61 = new UntilPredicate ( new AlwaysPastPredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Eventually ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 4 ), new Eventually ( new StrongPrevious ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), 3 );

        Query query62 = new Conjunction ( new AlwaysPastPredicate ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 ), new AlwaysPredicate ( new StrongPreviousPredicate ( new StrongPrevious ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 9 ), 5 ) );

        Query query63 = new EventuallyPredicate ( new StrongNextPredicate ( new WeakPrevious ( new EventuallyPredicate ( new StrongPrevious ( new Eventually ( new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ) ) ), 4 ) ), 4 ), 2 );

        Query query64 = new SincePredicate ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ), 6 ), new AlwaysPast ( new StrongNextPredicate ( new AlwaysPastPredicate ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), 4 ), 4 ) ), 8 );

        Query query65 = new StrongPrevious ( new StrongNextPredicate ( new AlwaysPast ( new EventuallyPastPredicate ( new EventuallyPredicate ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 8 ), 1 ), 7 ) ), 1 ) );

        Query query66 = new Until ( new AlwaysPredicate ( new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 ), new EventuallyPredicate ( new StrongPrevious ( new WeakNext ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), 5 ) );

        Query query67 = new StrongNext ( new AlwaysPast ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AlwaysPast ( new AlwaysPastPredicate ( new WeakPreviousPredicate ( new WeakPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 9 ), 2 ) ) ) ) );

        Query query68 = new AlwaysPastPredicate ( new AlwaysPredicate ( new WeakNextPredicate ( new StrongPrevious ( new WeakPrevious ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) ), 2 ), 7 ), 8 );

        Query query69 = new Conjunction ( new WeakNextPredicate ( new WeakNextPredicate ( new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ), 2 ), 8 ), new StrongNext ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 2 ) ) ) );

        Query query70 = new AlwaysPredicate ( new UntilPredicate ( new Eventually ( new EventuallyPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new StrongPrevious ( new Eventually ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ) ) ), 0 ), 7 );


        Query query71 = new AlwaysPast ( new Until ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), new StrongNext ( new EventuallyPredicate ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakNext ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), 2 ) ) ) );

        Query query72 = new StrongPrevious ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Until ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Disjunction ( new StrongPrevious ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ) ), new EventuallyPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) );

        Query query73 = new StrongPrevious ( new EventuallyPastPredicate ( new WeakPrevious ( new WeakNext ( new AlwaysPastPredicate ( new AlwaysPredicate ( new Always ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 1 ), 3 ) ) ), 8 ) );

        Query query74 = new AlwaysPast ( new EventuallyPastPredicate ( new WeakNextPredicate ( new AlwaysPastPredicate ( new Always ( new AlwaysPredicate ( new StrongPreviousPredicate ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 ), 8 ) ), 8 ), 7 ), 0 ) );

        Query query75 = new StrongPrevious ( new Until ( new StrongNext ( new EventuallyPast ( new StrongNextPredicate ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 4 ) ) ), new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ) ) );

        Query query76 = new StrongNextPredicate ( new SincePredicate ( new Disjunction ( new WeakPrevious ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new StrongNext ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 2 ) ) ), new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), 4 ), 6 );

        Query query77 = new EventuallyPastPredicate ( new Conjunction ( new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), new Eventually ( new Eventually ( new SincePredicate ( new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 1 ) ) ) ), 5 );

        Query query78 = new WeakNextPredicate ( new Always ( new AlwaysPredicate ( new WeakNextPredicate ( new UntilPredicate ( new AlwaysPast ( new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 6 ), 3 ), 5 ) ), 3 );

        Query query79 = new StrongPrevious ( new EventuallyPredicate ( new WeakPrevious ( new StrongPrevious ( new WeakPrevious ( new Disjunction ( new Until ( new WeakPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) ), 2 ) );

        Query query80 = new WeakPreviousPredicate ( new Since ( new SincePredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakNextPredicate ( new EventuallyPastPredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 7 ), 5 ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), new WeakPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 3 );


        Query query81 = new WeakNext ( new Conjunction ( new StrongPrevious ( new StrongPreviousPredicate ( new Conjunction ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ) ), new Since ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 2 ), new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ) ) ) );

        Query query82 = new AlwaysPredicate ( new AlwaysPast ( new Eventually ( new AlwaysPastPredicate ( new Eventually ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Always ( new Eventually ( new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), 3 ) ), 6 ) ) ), 9 );

        Query query83 = new Always ( new Always ( new Since ( new Always ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), 9 ) ), new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Always ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ) ), 7 ) ) ) );

        Query query84 = new AlwaysPastPredicate ( new Eventually ( new WeakNextPredicate ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new EventuallyPredicate ( new WeakPreviousPredicate ( new SincePredicate ( new EventuallyPredicate ( new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ), 5 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 6 ), 5 ), 3 ), 7 ), 4 ) ), 8 );

        Query query85 = new AlwaysPastPredicate ( new EventuallyPast ( new WeakNextPredicate ( new UntilPredicate ( new Conjunction ( new WeakNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 2 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new StrongPreviousPredicate ( new AlwaysPastPredicate ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 2 ), 1 ), 8 ), 5 ) ), 5 );

        Query query86 = new Eventually ( new AlwaysPastPredicate ( new UntilPredicate ( new StrongNextPredicate ( new EventuallyPastPredicate ( new StrongNextPredicate ( new WeakPreviousPredicate ( new Eventually ( new WeakPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 0 ), 8 ), 8 ), 7 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ), 1 ) );

        Query query87 = new Conjunction ( new WeakPreviousPredicate ( new StrongPrevious ( new StrongPreviousPredicate ( new Eventually ( new WeakPreviousPredicate ( new WeakNextPredicate ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 4 ), 7 ) ), 1 ) ), 1 ), new StrongPrevious ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) );

        Query query88 = new StrongNextPredicate ( new StrongPreviousPredicate ( new Since ( new SincePredicate ( new Until ( new AlwaysPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 1 ), new WeakNextPredicate ( new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 7 ), 6 ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), new SincePredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ) ), 4 ), 2 );

        Query query89 = new EventuallyPredicate ( new StrongNext ( new Conjunction ( new EventuallyPastPredicate ( new WeakNextPredicate ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AlwaysPastPredicate ( new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), 8 ) ), 4 ), 4 ), new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ), 5 );

        Query query90 = new Disjunction ( new WeakNextPredicate ( new Eventually ( new Disjunction ( new Until ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new WeakPreviousPredicate ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 1 ), 3 ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 1 ), new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ) );


        Query query91 = new AlwaysPredicate ( new Since ( new Always ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ) ), new StrongPreviousPredicate ( new AlwaysPast ( new AlwaysPredicate ( new AlwaysPredicate ( new WeakPreviousPredicate ( new AlwaysPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 1 ), 8 ), 0 ) ), 1 ) ), 2 );

        Query query92 = new AlwaysPast ( new UntilPredicate ( new SincePredicate ( new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AlwaysPastPredicate ( new StrongPreviousPredicate ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), 1 ), 3 ), 2 ), 5 ), new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new WeakPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), 3 ), 6 ) );

        Query query93 = new Conjunction ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), new StrongPreviousPredicate ( new WeakPrevious ( new AlwaysPredicate ( new Always ( new StrongNext ( new StrongPrevious ( new Until ( new Disjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) ), 5 ) ), 1 ) );

        Query query94 = new StrongNext ( new WeakPreviousPredicate ( new WeakNextPredicate ( new StrongPrevious ( new StrongPreviousPredicate ( new StrongPreviousPredicate ( new StrongPrevious ( new StrongNext ( new EventuallyPastPredicate ( new Until ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 3 ) ) ), 7 ), 1 ) ), 8 ), 7 ) );

        Query query95 = new WeakNext ( new StrongPrevious ( new EventuallyPastPredicate ( new EventuallyPast ( new EventuallyPredicate ( new Disjunction ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 0 ), new EventuallyPastPredicate ( new Always ( new Conjunction ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), 3 ) ), 2 ) ), 6 ) ) );

        Query query96 = new Until ( new Eventually ( new Since ( new StrongNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new SincePredicate ( new WeakPrevious ( new WeakNext ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 5 ), 7 ) ) ), new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 3 ) );

        Query query97 = new EventuallyPastPredicate ( new Conjunction ( new EventuallyPastPredicate ( new WeakNext ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 1 ), new AlwaysPredicate ( new AlwaysPast ( new AlwaysPast ( new SincePredicate ( new AlwaysPastPredicate ( new UntilPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ), 0 ), new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 1 ) ) ), 1 ) ), 1 );

        Query query98 = new StrongPreviousPredicate ( new WeakPrevious ( new UntilPredicate ( new UntilPredicate ( new Disjunction ( new EventuallyPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ), new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 3 ) ), new EventuallyPastPredicate ( new StrongNextPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 7 ), 2 ), 6 ), new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ), 7 ) ), 0 );

        Query query99 = new AlwaysPast ( new StrongNext ( new StrongNext ( new Conjunction ( new Always ( new Since ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), new Always ( new WeakNext ( new AlwaysPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ) ) ) ) ), new Always ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ) ) ) );

        Query query100 = new WeakNext ( new Disjunction ( new AlwaysPast ( new Eventually ( new WeakNext ( new EventuallyPastPredicate ( new EventuallyPastPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 8 ), 7 ) ) ) ), new Eventually ( new EventuallyPredicate ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 9 ), 3 ) ) ) );

/*
        SubqueryCounter subqueryCounter = new SubqueryCounter();
        System.out.println(subqueryCounter.getResult( query79 ));
*/


        StringBuilder sb = new StringBuilder();
        sb.append( "Zeitpunkt,Zus. Einträge,Ges. Zus.,Gesamt,#Antworten,Zeit in Millis,#Tabellen\n" );

        ArrayList<Integer> gesamt = new ArrayList<>();
        ArrayList<Integer> entries = new ArrayList<>();
        /*
        entries.add( 0, 25816 );
        entries.add( 1, 31951 );
        entries.add( 2, 34956 );
        entries.add( 3, 36860 );
        entries.add( 4, 39563 );
        entries.add( 5, 41271 );
        entries.add( 6, 41346 );
        entries.add( 7, 34684 );
        entries.add( 8, 26864 );
        entries.add( 9, 40421 );
        */
        entries.add( 0, 23914 );
        entries.add( 1, 26911 );
        entries.add( 2, 28398 );
        entries.add( 3, 30331 );
        entries.add( 4, 30195 );
        entries.add( 5, 31433 );
        entries.add( 6, 31676 );
        entries.add( 7, 32881 );
        entries.add( 8, 32805 );
        entries.add( 9, 34551 );
        entries.add( 10, 33890 );
        entries.add( 11, 35338 );
        entries.add( 12, 36146 );
        entries.add( 13, 36477 );
        entries.add( 14, 37712 );
        entries.add( 15, 38119 );
        entries.add( 16, 40893 );
        entries.add( 17, 41288 );
        entries.add( 18, 41410 );
        entries.add( 19, 40893 );
        entries.add( 20, 41557 );
        entries.add( 21, 41885 );
        entries.add( 22, 44084 );
        entries.add( 23, 44504 );
        entries.add( 24, 44517 );

        for ( int i = 0; i < 25; i++ ) {
            System.out.println( i+"----------------------------------------------------" );
            long time = System.currentTimeMillis();
            DataPhi phi = functionPhi.compute( i, query88 );
            subquerySaver.saveSubqueries( phi );
            //TODO grundsätzlich müsste das gehen. Unbedingt beobachten!
            subquerySaver.deleteAllOldSubqueries( i );
            DataNF result = phi.getDataNF();
            int size = eval.eval( result );
            for ( DataPhi subPhi : subquerySaver.getSavedSubqueries() ) {
                if ( !subPhi.isEvaluated() ) {
                    DataNF updatedDataNF = eval.evalSubquery( subPhi );
                    subPhi.setDataNF( updatedDataNF );
                    subPhi.setEvaluated( true );
                }
            }
            time = System.currentTimeMillis() - time;
            ArrayList<Integer> sizes = new ArrayList<>();
            System.out.println( "Time: " + time );
            //TODO remove println
            System.out.println( "Subqueries after doing everything at " + i + ":" );
            for ( DataPhi subPhi : subquerySaver.getSavedSubqueries() ) {
                System.out.println( subPhi );
            }
            for ( DataPhi subPhi : subquerySaver.getSavedSubqueries() ) {
                for ( HashSet<Variable> k : subPhi.getDataNF().keySet() ) {
                    for ( HashSet<AnswerTerm> h : subPhi.getDataNF().get( k ) ) {
                        for ( AnswerTerm t : h ) {
                            if (t.toString().contains( "result" )){
                                try {
                                    Class.forName( "org.sqlite.JDBC" );
                                    Connection conn = DriverManager.getConnection( "jdbc:sqlite:db.sqlite" );
                                    Statement st = conn.createStatement();
                                    ResultSet rs = st.executeQuery( "SELECT COUNT() FROM ("+t.toString()+")" );
                                    while ( rs.next() ) {
                                        String column_1 = rs.getString( "COUNT()" );
                                        //TODO remove println
                                        System.out.println(column_1);
                                        sizes.add( Integer.parseInt( column_1 ) );
                                    }
                                    st.close();
                                    conn.close();
                                } catch ( Exception e ) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            System.out.println( "----------------------------------------------------" );
            if (sizes.size() == 0){
                gesamt.add( i, entries.get( i ) );
                sb.append( i+",0,0,"+entries.get( i )+","+size+","+time+",1\n" );
            } else {
                boolean f = true;
                int geszus = 0;
                int tabs = sizes.size()+1;
                for (int k : sizes){
                    geszus+=k;
                }
                gesamt.add( i, geszus+entries.get( i ) );
                for ( int k : sizes ) {
                    if (f){
                        f = false;
                        sb.append( i+","+k+","+geszus+","+gesamt.get( i )+","+size+","+time+","+tabs+"\n" );
                    } else {
                        sb.append( ","+k+",,,,,\n" );
                    }
                }
            }
            subquerySaver.dropTables();
            File file = new File("output.csv");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file, true));
                writer.append( sb );
            } finally {
                if (writer != null) writer.close();
            }
            sb = new StringBuilder();
        }
        sb.append( "Zeitpunkt,Einträge,EinträgeSum,Gesamt\n" );
        int sum = 0;
        for ( int i = 0; i < 25; i++ ) {
            sum += entries.get( i );
            sb.append( i+","+entries.get( i )+","+sum+","+gesamt.get( i )+"\n" );
        }
        sb.append( ",,,\n" );
        File file = new File("output.csv");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.append( sb );
        } finally {
            if (writer != null) writer.close();
        }

        /**
         * At this point some example Queries are generated that are mainly used for quick testing while developing.
         * These examples should be replaced by real queries. These real queries may be generated by processing real input.
         */
        //Query query = new Disjunction( new Disjunction( new Conjunction( new Conjunction( new StrongPrevious( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table0" ) ), new AtemporalQuery( "SELECT * FROM table1" ) ), new Conjunction( new Conjunction( new WeakPrevious( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table2" ) ) ), new Conjunction( new Conjunction( new StrongNext( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table3" ) ), new Conjunction( new WeakNext( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table4" ) ) ) );

        //Query query = new Disjunction(new Conjunction( new Conjunction( new WeakNext( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table2" ) ) , new AtemporalQuery( "SELECT * FROM table3" ) ), new Conjunction( new Conjunction( new StrongNext( new AtemporalQuery( "SELECT * FROM table1" ) ), new AtemporalQuery( "SELECT * FROM table3" ) ) , new AtemporalQuery( "SELECT * FROM table4" ) ) );
        //Query query = new StrongPrevious( new StrongNext( new AtemporalQuery( "A" ) ) );
        //Query query = new WeakPrevious( new StrongNext( new AtemporalQuery( "A" ) ) );
        //Query query = new StrongPrevious( new Conjunction( new StrongNext( new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table1" ) ) ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new Since( new AtemporalQuery( "a" ), new WeakNext( new AtemporalQuery( "b" ) ) );
        //Query query = new Conjunction( new StrongPrevious( new StrongPrevious( new AtemporalQuery( "SELECT * FROM table1" ) ) ), new Since( new AtemporalQuery( "SELECT * FROM table1" ), new StrongNext( new AtemporalQuery( "SELECT * FROM table2" ) ) ) );
        //Query query = new Disjunction( new Disjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ) ), new Conjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new AtemporalQuery( "SELECT * FROM Table2" ) ) );
        //Query query = new Conjunction( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongPrevious( new Until( new StrongPrevious( new AtemporalQuery( "SELECT * FROM Table2" ) ), new AtemporalQuery( "SELECT * FROM Table3" ) ) ) );


        //Query query = new Since( new AtemporalQuery( "SELECT * FROM Table1" ), new StrongNext( new Conjunction( new AtemporalQuery( "SELECT * FROM Table2" ), new StrongNext( new StrongNext( new AtemporalQuery( "SELECT * FROM Table3" ) ) ) ) ) );

        /**
         * This part basically does all the work at the moment:
         * - calculating PhiI
         * - saving the subqueries
         * - evaluating the result (AnswerTerm of PhiI)
         *//*
        for ( int i = 0; i < 5; i++ ) {
            DataPhi phi = functionPhi.compute( i, query );
            subquerySaver.saveSubqueries( phi );
            *//*
            TODO
             Mit der unten stehenden Anweisung bekommt man die DataNF der DataPhi.
             Diese DataNF kann dann an eval übergeben werden.
             Dort wird aus der DataNF eine SQL-Query gebaut und anschließend abgefragt.
             *//*
            DataNF result = phi.getDataNF();
            // TODO println entfernen
            //System.out.println( "AT: " + phi.getAnswerTerm() );
            System.out.println( "DataNF: " + phi.getDataNF() );
            eval.eval( result );
            for ( DataPhi subPhi : subquerySaver.getSavedSubqueries() ) {
                if(!subPhi.isEvaluated()){
                    DataNF updatedDataNF = eval.evalSubquery( subPhi.getDataNF() );
                    subPhi.setDataNF( updatedDataNF );
                    subPhi.setEvaluated( true );
                }
                // TODO println entfernen
                System.out.println( phi );
            }
        }
        System.out.println( subquerySaver.getSavedSubqueries().size() );

*/
/*
        AnswerTerm answerTerm = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "A" ), 0 ), new AnswerTermDisjunction( new AnswerTermConjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerTermDisjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerSet( new AtemporalQuery( "B" ), 0 ) ) ), new AnswerSet( new AtemporalQuery( "C" ), 0 ) ) ),
                new AnswerTermDisjunction( new AnswerSet( new AtemporalQuery( "D" ), 0 ), new Variable( 0, new AtemporalQuery( "Y" ) ) ) );
        NormalForm normalForm = new NormalForm();
        AnswerTerm answerTerm1 = normalForm.prepareNF( answerTerm );
        AnswerTerm answerTerm2 = new AnswerTermConjunction( new AnswerTermConjunction( new AnswerSet( new AtemporalQuery( "A" ), 0 ), new AnswerTermDisjunction( new AnswerTermConjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerTermDisjunction( new Variable( 0, new AtemporalQuery( "X" ) ), new AnswerSet( new AtemporalQuery( "B" ), 0 ) ) ), new AnswerSet( new AtemporalQuery( "C" ), 0 ) ) ),
                new AnswerTermDisjunction( answerTerm1, new Variable( 0, new AtemporalQuery( "Y" ) ) ) );
        normalForm.prepareNF( answerTerm2 );
  */
    }

}
