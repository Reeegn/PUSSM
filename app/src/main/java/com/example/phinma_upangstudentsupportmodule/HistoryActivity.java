package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<User> list;
    FirebaseUser user;
    String userID;
    HashMap<String, HashMap<String, String>> data1;

    private ProgressDialog progressDialog;
    private DatabaseReference rootReference;
    String AY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        data1 = new HashMap<>();

        progressDialog = ProgressDialog.show(HistoryActivity.this, null, null);
        progressDialog.setContentView(new ProgressBar(HistoryActivity.this));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rootReference = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Log.d("getdata", "fetching data");
        getData();

//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                    User user = dataSnapshot.getValue(User.class);
//                    list.add(user);
//                }
//                myAdapter.notifyDataSetChanged();
//                Collections.reverse(list);
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        //Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.history);

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
                        startActivity(new Intent(getApplicationContext(), AssessmentScreenActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
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

    private void getData() {
        rootReference.child("system/current").get().addOnCompleteListener(task -> {
            if(task.isComplete()) {
                AY = task.getResult().getValue().toString();
                rootReference.child("data/"+AY+"/student/"+userID).get().addOnCompleteListener(task1 -> {
                    if(task1.isComplete()) {
                        if (task1.getResult().hasChild("subject")) {
                            String subj = task1.getResult().child("subject").getValue().toString();
                            String sect = task1.getResult().child("section").getValue().toString();

                            rootReference.child("data/" + AY + "/studentList/" + subj + "/" + sect + "/" + userID + "/result").get().addOnCompleteListener(task2 -> {
                                if (task2.isComplete()) {
                                    if (task2.getResult().exists()) {
                                        Map<String, String> data = (Map<String, String>) task2.getResult().getValue();
                                        for (String v:data.keySet()) {
                                            rootReference.child("data/" + AY+ "/result/"+v).get().addOnCompleteListener(task3 -> {
//                                                Date d = new Date(t.getTime());
//                                                textview_physical.setText(task3.getResult().child("physical").getValue().toString());
//                                                textview_mental.setText(task3.getResult().child("mental").getValue().toString());
//                                                textview_overall.setText(task3.getResult().child("total").getValue().toString());
                                                Timestamp t = new Timestamp(Long.valueOf(v));
                                                HashMap<String, String> innerData = new HashMap<>();
                                                innerData.put("date", new Date(t.getTime()).toString());
                                                innerData.put("physical",task3.getResult().child("physical").getValue().toString());
                                                innerData.put("mental",task3.getResult().child("mental").getValue().toString());
                                                innerData.put("overall",task3.getResult().child("total").getValue().toString());
                                                data1.put(v, innerData);

                                                if(data.size() == data1.size()) {
                                                    progressDialog.dismiss();
                                                    recyclerView = findViewById(R.id.historyList);
                                                    database = FirebaseDatabase.getInstance().getReference("users")
                                                            .child(userID)
                                                            .child("result");
                                                    recyclerView.setHasFixedSize(true);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(this));

//                                                  list = new ArrayList<>();
                                                    myAdapter = new MyAdapter(this, data1);
                                                    recyclerView.setAdapter(myAdapter);
                                                }
                                            });
                                        }
                                    } else {
//                                        Student has no result
                                    }
                                }
                            });
                        } else {
//                            Student not yet enrolled
                        }
                    }
                });
            }
        });
    }
}