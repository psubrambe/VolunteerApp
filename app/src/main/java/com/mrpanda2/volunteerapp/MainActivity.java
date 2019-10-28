package com.mrpanda2.volunteerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        volunteerSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SignInUser();
            }
        });
        organizationSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, organizationSignInPage.class);
                startActivity(intent);
            }
        });

    }
    public void SignInUser(){
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
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(MainActivity.this, "Sign in successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(MainActivity.this, VolunteerSignInActivity.class);
                                startActivity(intent);
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

    @Override
    public void onClick(View view) {

    }
}
