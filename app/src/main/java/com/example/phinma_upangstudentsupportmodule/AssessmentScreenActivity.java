package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AssessmentScreenActivity extends AppCompatActivity {

    private String selectedTestName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_screen);

        /*
        //initialize widgets from activity_main.xml
        final LinearLayout physical = findViewById(R.id.physicalLayout);
        final LinearLayout mental = findViewById(R.id.mentalLayout);
        final Button startBtn = findViewById(R.id.startAssessmentBtn);

        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign Physical Assessment to selectedTestName
                selectedTestName = "physical";

                //select physical layout
                physical.setBackgroundResource(R.drawable.round_back_gold_stroke);
                //de-select other layout
                mental.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //assign Mental Assessment to selectedTestName
                selectedTestName = "mental";

                //select mental layout
                mental.setBackgroundResource(R.drawable.round_back_gold_stroke);
                //de-select other layout
                physical.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

         */
        /*
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if user has not selected any topic yet then show a Toast message
                selectedTestName will be empty or default value ("") if user has not selected any topic yet
                if(selectedTestName.isEmpty()){
                    Toast.makeText(AssessmentScreenActivity.this, "Please select assessment", Toast.LENGTH_SHORT).show();
                }
                else{

                    //Create an object of Intent to open assessment questions screen
                    Intent intent = new Intent(AssessmentScreenActivity.this, AssessmentActivity.class);

                    //put user entered name and selected test name to intent for use in the next activity
                    intent.putExtra("selectedTest", selectedTestName);
                    startActivity(intent);
                }
            }
        });
        */

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        //Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.assessment);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.assessment:
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}