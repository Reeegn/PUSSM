package com.example.phinma_upangstudentsupportmodule;

public class QuestionsList {

//    DEVLOG: Added option 5 for the 5th option. Questions should follow ALWAYS, OFTEN, SOMETIMES, OCCASIONALLY, NEVER.
    private String question, option1, option2, option3, option4, option5;
    private String userSelectedAnswer;

    public QuestionsList(String question, String option1, String option2, String option3, String option4,String option5, String userSelectedAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.userSelectedAnswer = userSelectedAnswer;
    }

    public QuestionsList(String getQuestion, String getOption1, String getOption2, String getOption3, String getOption4, String getOption5) {
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getOption5() {
        return option5;
    }

    public String getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    public void setUserSelectedAnswer(String userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }
}
