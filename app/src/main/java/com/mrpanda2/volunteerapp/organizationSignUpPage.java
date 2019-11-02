package com.mrpanda2.volunteerapp;

import android.content.Intent;
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

public class organizationSignUpPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mName;
    private Button mOrgSignUpButton;
    private Organization mOrganization;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.organization_sign_up);
        mEmail = findViewById(R.id.OrgEmail);
        mPassword = findViewById(R.id.OrgPassword);
        mName = findViewById(R.id.OrgName);
        mOrgSignUpButton = findViewById(R.id.OrgSignUpButton);

        mOrgSignUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                RegisterOrg();
            }
        });
    }

    public void RegisterOrg(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        final String name = mName.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(organizationSignUpPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try{
                            //check if successful
                            if (task.isSuccessful()){
                                //Set User
                                mUser = FirebaseAuth.getInstance().getCurrentUser();

                                //Organization Object created for Firebase
                                mOrganization = new Organization();
                                mOrganization.setId(mUser.getUid());
                                mOrganization.setName(name);

                                //User displayName is added to user's authentication
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();

                                mUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Log.d("TAG", "Org Profile Updated.");
                                                }
                                            }
                                        });
                                //User is successfully registered and logged in
                                //Start Profile Activity Here
                                Toast.makeText(organizationSignUpPage.this, "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();

                                //Add User to Organization Group in Firebase
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("organizations").child(mUser.getUid()).setValue(mOrganization);

                                Intent intent = new Intent(organizationSignUpPage.this, OrgProfileFragment.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(organizationSignUpPage.this, "Couldn't register, try again.", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });


    }


}
