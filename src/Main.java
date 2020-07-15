import results.*;
import computing.*;
import queries.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main( String[] args ) throws IOException {

        SubquerySaver subquerySaver = new SubquerySaver();
        FunctionPhi functionPhi = new FunctionPhi( subquerySaver );
        Eval eval = new Eval();

        Query query1 = new AlwaysPredicate ( new UntilPredicate ( new Eventually ( new EventuallyPast ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ) ) ), new StrongPrevious ( new Eventually ( new StrongPreviousPredicate ( new AtemporalQuery( "SELECT url FROM autos WHERE NOT deleted" ), 4 ) ) ), 0 ), 7 );

        StringBuilder sb = new StringBuilder();
        sb.append( "Zeitpunkt,Zus. Einträge,Ges. Zus.,Gesamt,#Antworten,Zeit in Millis,#Tabellen\n" );

        ArrayList<Integer> gesamt = new ArrayList<>();
        ArrayList<Integer> entries = new ArrayList<>();
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



        for ( int i = 0; i < 10; i++ ) {
            System.out.println( "----------------------------------------------------" );
            long time = System.currentTimeMillis();
            DataPhi phi = functionPhi.compute( i, query1 );
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
        }
        sb.append( "Zeitpunkt,Einträge,EinträgeSum,Gesamt\n" );
        for ( int i = 0; i < 10; i++ ) {
            sb.append( i+","+entries.get( i )+",,"+gesamt.get( i )+"\n" );
        }
        System.out.println(sb.toString());
        File file = new File("output.csv");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
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
