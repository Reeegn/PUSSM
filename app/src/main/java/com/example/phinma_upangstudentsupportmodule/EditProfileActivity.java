package com.example.phinma_upangstudentsupportmodule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button update;
    private Button changePicture;
    private ImageView profilePicture;

    StorageReference storageReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog = ProgressDialog.show(EditProfileActivity.this, null, null);
        progressDialog.setContentView(new ProgressBar(EditProfileActivity.this));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize firebase variables to get name
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = user.getUid();

        StorageReference profileRef = storageReference.child("profileImages")
                .child(userID)
                .child("profile.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePicture);
                progressDialog.dismiss();
            }
        });

        //Welcome message
        final TextView fullNameTextView = (TextView) findViewById(R.id.full_name);
        final TextView studentNumberTextView = (TextView) findViewById(R.id.student_number);
        final TextInputEditText emailTextView = (TextInputEditText) findViewById(R.id.email);
        final TextInputEditText sectionTextView = (TextInputEditText) findViewById(R.id.section);
        final TextInputEditText contactNumberTextView = (TextInputEditText) findViewById(R.id.contact_number);
        final TextInputEditText departmentTextView = (TextInputEditText) findViewById(R.id.department);

        profilePicture = (ImageView) findViewById(R.id.profile_picture);
        changePicture = (Button) findViewById(R.id.change_picture);
        update = (Button) findViewById(R.id.edit_info);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactNumberTextView.getText().toString();
                reference.child(userID).child("contact").setValue(contact);
                Toast.makeText(EditProfileActivity.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                finish();
            }
        });

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profilePicture.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {

        progressDialog = ProgressDialog.show(EditProfileActivity.this, null, null);
        progressDialog.setContentView(new ProgressBar(EditProfileActivity.this));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        StorageReference fileRef = storageReference.child("profileImages")
                .child(userID)
                .child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePicture);
                        Toast.makeText(EditProfileActivity.this, "Picture has been updated", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
    }
}