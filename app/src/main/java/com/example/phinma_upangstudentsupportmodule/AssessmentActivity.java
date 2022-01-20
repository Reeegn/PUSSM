package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {

    //Total questions and main question TextView
    private TextView questions;
    private TextView question;

    //options
    private AppCompatButton option1, option2, option3, option4;

    //next button
    private AppCompatButton nextBtn;

    //questions array list
    private List<QuestionsList> questionsLists;

    //Current questions index position from questionsLists ArrayList
    private int currentQuestionPosition = 0;

    //selectedOption's value. If user not selected any option yet then it is empty by default
    private String selectedOptionByUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        //Initialize widgets from activity_main.xml file
        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView selectedTestName = findViewById(R.id.AssessmentName);
        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.nextBtn);

        //Get test name from MainActivity via Intent
        final String getSelectedTestName = getIntent().getStringExtra("selectedTest");

        //Set test name to TextView
        selectedTestName.setText(getSelectedTestName);

        /*
        //Get questions from Firebase Database according to the selectedTestName and assign questionsLists ArrayList
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://assess-module-default-rtdb.firebaseio.com/");

        //show dialog while questions are being fetched
        ProgressDialog progressDialog = new ProgressDialog(AssessmentActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Getting all questions from Firebase Database for a specific test
                for (DataSnapshot dataSnapshot : snapshot.child(getSelectedTestName).getChildren()){

                    //Getting data from Firebase Database
                    final String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    final String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    final String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    final String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    final String getOption4 = dataSnapshot.child("option4").getValue(String.class);

                    //Adding data to the questionsLists
                    QuestionsList questionsList = new QuestionsList(getQuestion, getOption1, getOption2, getOption3, getOption4);
                    questionsLists.add(questionsList);
                }
                //hide dialog
                progressDialog.hide();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        //For debugging
        questionsLists = QuestionsBank.getQuestions(getSelectedTestName);

        //Set current questions to TextView along with options from questionsLists ArrayList
        questions.setText((currentQuestionPosition+1)+"/"+questionsLists.size());
        question.setText(questionsLists.get(0).getQuestion());
        option1.setText(questionsLists.get(0).getOption1());
        option2.setText(questionsLists.get(0).getOption2());
        option3.setText(questionsLists.get(0).getOption3());
        option4.setText(questionsLists.get(0).getOption4());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();

                    //change selected AppCompatButton background color and text color
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);

                    //assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();

                    //change selected AppCompatButton background color and text color
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);

                    //assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();

                    //change selected AppCompatButton background color and text color
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);

                    //assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user has not attempted this question yet
                if (selectedOptionByUser.isEmpty()){
                    selectedOptionByUser = option1.getText().toString();

                    //change selected AppCompatButton background color and text color
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);

                    //assign user answer value to userSelectedOption in QuestionsList class
                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);

                }

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if user has not selected any option yet
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(AssessmentActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AssessmentActivity.this, MainActivity.class));
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AssessmentActivity.this, MainActivity.class));
        finish();
    }

    private void changeNextQuestion(){
        currentQuestionPosition++;
        if ((currentQuestionPosition+1) == questionsLists.size()) {
            nextBtn.setText("Submit Assessment");
        }

        if ((currentQuestionPosition < questionsLists.size())) {
            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.round_back_white_stroke10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionsLists.size());
            question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
        }
        else {
            Intent intent = new Intent(AssessmentActivity.this, AssessmentResults.class);
            startActivity(intent);

            finish();
        }
    }
}