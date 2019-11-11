package computing;

public class TermDisjunction implements Term {

    Term term1;
    Term term2;


    public TermDisjunction( Term term1, Term term2 ) {
        this.term1 = term1;
        this.term2 = term2;
    }

    public Term getTerm1() {
        return term1;
    }

    public Term getTerm2() {
        return term2;
    }

    @Override
    public String toString() {
        //return "(" + term1.toString() + "\u001B[32m" + " u " + "\u001B[0m" + term2.toString() + ")";
        return "(" + term1.toString() + " u " + term2.toString() + ")";
    }
}
