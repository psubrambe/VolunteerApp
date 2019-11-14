package com.mrpanda2.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class volunteerSignUpPage extends AppCompatActivity implements View.OnClickListener {
    Button volunteerSignInButton;
    Button organizationSignInButton;
    Button volunteerSignUpButton;
    Button organizationSignUpButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private EditText email;
    private EditText password;
    private EditText mName;
    private Volunteer mVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.volunteer_sign_up);
        email =  findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        mName = findViewById(R.id.Name);
        volunteerSignUpButton = findViewById(R.id.volunteerSignUpButton);

        volunteerSignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SharedPreferences sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.typeid), "vol");
                editor.commit();
                RegisterUser();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
    public void RegisterUser(){
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        final String Name = mName.getText().toString();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Name.isEmpty()){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(volunteerSignUpPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {

                                //Set User
                                mUser = FirebaseAuth.getInstance().getCurrentUser();

                                //Volunteer Object created for Firebase
                                mVolunteer = new Volunteer();
                                mVolunteer.setId(mUser.getUid());
                                mVolunteer.setName(Name);

                                //User displayName is added to user's authentication
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(Name).build();

                                mUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(volunteerSignUpPage.this, mUser.getDisplayName(),
                                                            Toast.LENGTH_SHORT).show();
                                                    Log.d("TAG", "Volunteer profile updated.");
                                                }
                                            }
                                        });

                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(volunteerSignUpPage.this, "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();

                                //Add User to Volunteer Group in Firebase
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("volunteers").child(mUser.getUid()).setValue(mVolunteer);

                                Intent intent = new Intent(volunteerSignUpPage.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(volunteerSignUpPage.this, "Couldn't register, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
