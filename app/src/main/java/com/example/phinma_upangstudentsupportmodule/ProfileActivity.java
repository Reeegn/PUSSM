package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;
    private Button changePassword;
    private Button update;
    private ImageView profile_picture;

    StorageReference storageReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = ProgressDialog.show(ProfileActivity.this, null, null);
        progressDialog.setContentView(new ProgressBar(ProfileActivity.this));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize firebase variables to get name
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = user.getUid();

        //Welcome message
        final TextView fullNameTextView = (TextView) findViewById(R.id.full_name);
        final TextView studentNumberTextView = (TextView) findViewById(R.id.student_number);
        final TextInputEditText emailTextView = (TextInputEditText) findViewById(R.id.email);
        final TextInputEditText sectionTextView = (TextInputEditText) findViewById(R.id.section);
        final TextInputEditText contactNumberTextView = (TextInputEditText) findViewById(R.id.contact_number);
        final TextInputEditText departmentTextView = (TextInputEditText) findViewById(R.id.department);

        logout = (Button) findViewById(R.id.logout);
        changePassword = (Button) findViewById(R.id.change_password);
        update = (Button) findViewById(R.id.edit_info);
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

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String firstName = userProfile.firstname;
                    String middleName = userProfile.middlename;
                    String lastName = userProfile.lastname;
                    String studentNumber = userProfile.idnumber;
                    String email = userProfile.email;
                    String section = userProfile.section;
                    String contactNumber = userProfile.contact;
                    String department = userProfile.department;

                    fullNameTextView.setText(firstName + " " + middleName + " " + lastName);
                    studentNumberTextView.setText(studentNumber);
                    emailTextView.setText(email);
                    sectionTextView.setText(section);
                    contactNumberTextView.setText(contactNumber);
                    departmentTextView.setText(department);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                finish();
            }

        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);

                EditText currentPasswordEt = (EditText) mView.findViewById(R.id.current_password);
                EditText newPasswordEt = (EditText) mView.findViewById(R.id.new_password);
                EditText confirmNewPasswordEt = (EditText) mView.findViewById(R.id.confirm_new_password);
                Button changePasswordBtn = (Button) mView.findViewById(R.id.change_password);

                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                changePasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currentPassword = currentPasswordEt.getText().toString().trim();
                        String newPassword = newPasswordEt.getText().toString().trim();
                        String confirmNewPassword = confirmNewPasswordEt.getText().toString().trim();

                        if (TextUtils.isEmpty(currentPassword)) {
                            currentPasswordEt.setError("Enter your current password");
                            currentPasswordEt.requestFocus();
                            return;
                        }
                        if (newPassword.length()<6){
                            newPasswordEt.setError("New password length must be atleast 6 characters");
                            newPasswordEt.requestFocus();
                            return;
                        }
                        if (newPassword.equals(confirmNewPassword)) {
                            updatePassword(currentPassword, newPassword);
                        } else {
                            newPasswordEt.setError("Passwords do not match!");
                            confirmNewPasswordEt.setError("Passwords do not match!");
                            newPasswordEt.requestFocus();
                            confirmNewPasswordEt.requestFocus();
                            return;
                        }
                    }

                    private void updatePassword(String currentPassword, String newPassword) {
                        //before changing the password, re-authenticate user
                        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
                        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //successfully authenticated, begin update
                                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, Login.class));
                finish();
            }
        });

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        //Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }
}