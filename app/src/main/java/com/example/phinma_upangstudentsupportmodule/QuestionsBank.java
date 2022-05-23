package com.example.phinma_upangstudentsupportmodule;

import java.util.ArrayList;
import java.util.List;

public class QuestionsBank {

    private static List<QuestionsList> physicalAssessment() {

        final List<QuestionsList> questionsLists = new ArrayList<>();
//        DEBUG: Questions should follow ALWAYS, OFTEN, SOMETIMES, OCCASIONALLY, NEVER
        //Create object of QuestionsList class and pass a questions along with options and answer
        final QuestionsList question1 = new QuestionsList("Are you having trouble breathing?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question2 = new QuestionsList("Do you have persistent pain or pressure in the chest?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question3 = new QuestionsList("Are you experiencing confusion", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question4 = new QuestionsList("Are you experiencing inability to wake or stay awake", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question5 = new QuestionsList("Do you have pale, gray, or blue-colored skin, lips, or nail beds, depending on skin tone", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question6 = new QuestionsList("Are you experiencing muscle aches or body aches?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question7 = new QuestionsList("Are you experiencing unusual fatigue?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question8 = new QuestionsList("Are you experiencing nausea or vomiting?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question9 = new QuestionsList("Are you having fever?", "Yes", "No", "Sometimes", "Not At All", "");
        final QuestionsList question10 = new QuestionsList("Do you have cough?", "Yes", "No", "Sometimes", "Not At All", "");

        //add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;
    }

    private static List<QuestionsList> mentalAssessment() {

        final List<QuestionsList> questionsLists = new ArrayList<>();

        //Create object of QuestionsList class and pass a questions along with options and answer
        final QuestionsList question1 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Little interest or pleasure in doing things", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question2 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Feeling down, depressed, or hopeless", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question3 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Trouble falling or staying asleep, or sleeping too much", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question4 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Feeling tired or having little energy", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question5 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Poor appetite or overeating", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question6 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Feeling bad about yourself - or that you are a failure or have let yourself or your family down", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question7 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Trouble concentrating on things, such as reading the newspaper or watching television", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question8 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Moving or speaking so slowly that other people could have noticed Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question9 = new QuestionsList("Over the last 2 weeks, how often have you been bothered by any of the following problems?\n Thoughts that you would be better off dead, or of hurting yourself", "Not at All", "Several Days", "More Than Half The Days", "Nearly Everyday", "");
        final QuestionsList question10 = new QuestionsList("If you checked off any problems, how difficult have these problems made it for you at work, home, or with other people?", "Not Difficult At All", "Somewhat Difficult", "Very Difficult", "Extremely Difficult", "");

        //add all questions to List<QuestionsList>
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;
    }

    public static List<QuestionsList> getQuestions(String selectedTestName) {
        switch (selectedTestName) {
            case "physical":
                return physicalAssessment();
            default:
                return mentalAssessment();
        }
    }
}
