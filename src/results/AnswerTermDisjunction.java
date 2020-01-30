package results;

public class AnswerTermDisjunction implements AnswerTerm {

    AnswerTerm answerTerm1;
    AnswerTerm answerTerm2;


    public AnswerTermDisjunction( AnswerTerm answerTerm1, AnswerTerm answerTerm2 ) {
        this.answerTerm1 = answerTerm1;
        this.answerTerm2 = answerTerm2;
    }

    public AnswerTerm getAnswerTerm1() {
        return answerTerm1;
    }

    public AnswerTerm getAnswerTerm2() {
        return answerTerm2;
    }

    @Override
    public String toString() {
        return "(" + answerTerm1.toString() + "\u001B[32m" + " u " + "\u001B[0m" + answerTerm2.toString() + ")";
        //return "(" + answerTerm1.toString() + " u " + answerTerm2.toString() + ")";
    }

}
