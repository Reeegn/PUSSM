package com.example.phinma_upangstudentsupportmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String selectedTestName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                physical.setBackgroundResource(R.drawable.round_back_gold_stroke10);
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
                mental.setBackgroundResource(R.drawable.round_back_gold_stroke10);
                //de-select other layout
                physical.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* if user has not selected any topic yet then show a Toast message
                selectedTestName will be empty or default value ("") if user has not selected any topic yet */
                if(selectedTestName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please select assessment", Toast.LENGTH_SHORT).show();
                }
                else{

                    //Create an object of Intent to open assessment questions screen
                    Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);

                    //put user entered name and selected test name to intent for use in the next activity
                    intent.putExtra("selectedTest", selectedTestName);
                    startActivity(intent);
                }
            }
        });
    }
}