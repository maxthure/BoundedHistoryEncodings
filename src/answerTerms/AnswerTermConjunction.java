package computing;

public class AnswerTermConjunction implements AnswerTerm {

    AnswerTerm answerTerm1;
    AnswerTerm answerTerm2;


    public AnswerTermConjunction( AnswerTerm answerTerm1, AnswerTerm answerTerm2 ) {
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
        //return "(" + answerTerm1.toString() + "\u001B[31m" + " n " + "\u001B[0m" + answerTerm2.toString() + ")";
        return "(" + answerTerm1.toString() + " n " + answerTerm2.toString() + ")";
    }
}
