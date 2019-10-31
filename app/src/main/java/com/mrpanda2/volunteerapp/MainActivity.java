package com.mrpanda2.volunteerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button volunteerSignInButton;
    Button organizationSignInButton;
    Button volunteerSignUpButton;
    Button organizationSignUpButton;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Log.d("Activity LifeCycle","Working!");

        volunteerSignInButton = findViewById(R.id.volunteerSignInButton);
        organizationSignInButton = findViewById(R.id.organizationSignInButton);
        organizationSignUpButton = findViewById(R.id.organizationSignUpButton);
        volunteerSignUpButton = findViewById(R.id.volunteerSignUpButton);


        volunteerSignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, volunteerSignUpPage.class);
                startActivity(intent);
            }
        });

        organizationSignUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, organizationSignUpPage.class);
                startActivity(intent);
            }
        });

        volunteerSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SignInVolunteer();
            }
        });

        organizationSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SignInOrganization();
            }
        });

    }
    public void SignInVolunteer(){
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        if (TextUtils.isEmpty(email.getText())){
            Toast.makeText(MainActivity.this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password.getText())){
            Toast.makeText(MainActivity.this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {

                                //Check that user is a volunteer, if not, sign out and notify.
                                final String mId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                FirebaseDatabase.getInstance().getReference().child("volunteers")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                    if (snapshot.getKey().equals(mId)){
                                                        SendToVolProfile();
                                                        return;
                                                    }
                                                }
                                                Toast.makeText(MainActivity.this, "Found user, but not Volunteer.",
                                                        Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });

                            }else{
                                Toast.makeText(MainActivity.this, "Couldn't Sign in, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void SignInOrganization(){
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        if (TextUtils.isEmpty(email.getText())){
            Toast.makeText(MainActivity.this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password.getText())){
            Toast.makeText(MainActivity.this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {

                                //Check that user is an organization, if not, sign out and notify.
                                final String mId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                FirebaseDatabase.getInstance().getReference().child("organizations")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                    if (snapshot.getKey().equals(mId)){
                                                        SendToOrgProfile();
                                                        return;
                                                    }
                                                }
                                                Toast.makeText(MainActivity.this, "Found user, but not Organization.",
                                                        Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });


                            }else{
                                Toast.makeText(MainActivity.this, "Couldn't Sign in, try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void SendToVolProfile(){
        //User is successfully registered and logged in
        //Save Volunteer status to shared preferences
        SharedPreferences sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.typeid), "vol");
        editor.commit();

        //start Profile Activity here
        Toast.makeText(MainActivity.this, "Sign in successful",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, VolunteerSignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void SendToOrgProfile(){
        //User is successfully registered and logged in
        //Save Organization status to shared preferences
        SharedPreferences sharedPref = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.typeid), "org");
        editor.commit();

        //start Profile Activity here
        Toast.makeText(MainActivity.this, "Sign in successful",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, organizationSignInPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

    }
}
