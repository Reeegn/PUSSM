package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://assess-module-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText studentNumber = findViewById(R.id.studentNumber);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String studentNumberTxt = studentNumber.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(studentNumberTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your student number or password", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if student number exists in Firebase Database
                            if (snapshot.hasChild(studentNumberTxt)) {

                                //student number exists in Firebase Database
                                //get password of user in Firebase Database and match it with user input
                                final String getPassword = snapshot.child(studentNumberTxt).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                                    //open MainActivity on success
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}