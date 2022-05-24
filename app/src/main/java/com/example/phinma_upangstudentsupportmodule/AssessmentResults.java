package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AssessmentResults extends AppCompatActivity {

    String uid;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_results);

        double physicalScore = getIntent().getDoubleExtra("physicalScore", 0),
            mentalScore = getIntent().getDoubleExtra("mentalScore", 0);

        double overScore = (physicalScore + mentalScore) / 2;

        ProgressBar progPhy = findViewById(R.id.progress_bar_physical),
                progMen = findViewById(R.id.progress_bar_mental),
                progOver = findViewById(R.id.progress_bar_overall);
        TextView textPhy = findViewById(R.id.text_view_progress_physical),
                textMen = findViewById(R.id.text_view_progress_mental),
                textOver = findViewById(R.id.text_view_progress_overall);

        progPhy.setProgress((int) Math.round(physicalScore));
        progMen.setProgress((int) Math.round(mentalScore));
        progOver.setProgress((int) Math.round(overScore));

        textPhy.setText(String.valueOf(Math.round(physicalScore)));
        textMen.setText(String.valueOf(Math.round(mentalScore)));
        textOver.setText(String.valueOf(Math.round(overScore)));

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("system/current").get().addOnCompleteListener(task -> {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("data/"+task.getResult().getValue().toString());
            String currAY = task.getResult().getValue().toString();
            dbRef.get().addOnCompleteListener(task1 -> {
                if(task1.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("users/"+uid).get().addOnCompleteListener(task2 -> {
                        Long tempTS = System.currentTimeMillis();
                        String ts = String.valueOf(tempTS);
                        DataSnapshot data = task1.getResult();
                        String sub = data.child("student/"+uid+"/subject").getValue().toString();
                        String sect = data.child("student/"+uid+"/section").getValue().toString();
                        HashMap<String, Object> stdList = new HashMap<>(),
                                assessmentDetails = new HashMap<>();
                        stdList.put(ts, "RECORDED");
                        dbRef.child("studentList/"+sub+"/"+sect+"/"+uid+"/result").updateChildren(stdList);

                        User userProfile = task2.getResult().getValue(User.class);

                        assessmentDetails.put("ay", Integer.valueOf(currAY.split("-")[0]));
                        assessmentDetails.put("contact", userProfile.contact);
                        assessmentDetails.put("email", userProfile.email);
                        assessmentDetails.put("firstname", userProfile.firstname);
                        assessmentDetails.put("lastname", userProfile.lastname);
                        assessmentDetails.put("mental", Math.round(mentalScore));
                        assessmentDetails.put("middlename", userProfile.middlename);
                        assessmentDetails.put("physical", Math.round(physicalScore));
                        assessmentDetails.put("section", sect);
                        assessmentDetails.put("status", "RECORDED");
                        assessmentDetails.put("subject", sub);
                        assessmentDetails.put("total", Math.round(overScore));
                        assessmentDetails.put("uid", uid);

                        dbRef.child("result/"+ts).updateChildren(assessmentDetails);
                    });
                }
            });
        });

        final AppCompatButton startNewBtn = findViewById(R.id.startNewAssessmentBtn);
        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssessmentResults.this, AssessmentScreenActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AssessmentResults.this, AssessmentScreenActivity.class));
        finish();
    }
}