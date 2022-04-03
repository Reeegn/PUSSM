package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ImageView profile_picture;
    private ArrayList<User> list;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*

         */

        //initialize firebase variables to get name
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = user.getUid();

        profile_picture = (ImageView) findViewById(R.id.profile_picture);

        StorageReference profileRef = storageReference.child("profileImages")
                .child(userID)
                .child("profile.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_picture);
            }
        });


        //Welcome message
        final TextView welcomeMessageTextView = (TextView) findViewById(R.id.welcomeMessage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String firstName = userProfile.firstname;

                    welcomeMessageTextView.setText(firstName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

        final TextView textview_date = (TextView) findViewById(R.id.dashboard_date);
        final TextView textview_physical = (TextView) findViewById(R.id.tv_progress_physical);
        final TextView textview_mental = (TextView) findViewById(R.id.tv_progress_mental);
        final TextView textview_overall = (TextView) findViewById(R.id.tv_progress_overall);
        final ProgressBar progress_physical = (ProgressBar) findViewById(R.id.pb_physical);
        final ProgressBar progress_mental = (ProgressBar) findViewById(R.id.pb_mental);
        final ProgressBar progress_overall = (ProgressBar) findViewById(R.id.pb_overall);

        DatabaseReference result_ref = FirebaseDatabase.getInstance().getReference("users").child(userID).child("result");

        result_ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String date = snapshot.child("date").getValue().toString();
                    String mental = snapshot.child("mental").getValue().toString();
                    String physical = snapshot.child("physical").getValue().toString();
                    String overall = snapshot.child("overall").getValue().toString();

                    String fMental = mental + "%";
                    String fPhysical = physical + "%";
                    String fOverall = overall + "%";

                    textview_date.setText(date);
                    textview_mental.setText(fMental);
                    textview_physical.setText(fPhysical);
                    textview_overall.setText(fOverall);
                    progress_mental.setProgress(Integer.parseInt(mental));
                    progress_physical.setProgress(Integer.parseInt(physical));
                    progress_overall.setProgress(Integer.parseInt(overall));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        //Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.assessment:
                        startActivity(new Intent(getApplicationContext(), AssessmentScreenActivity.class));
                        finish();
                        overridePendingTransition(0,0);
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