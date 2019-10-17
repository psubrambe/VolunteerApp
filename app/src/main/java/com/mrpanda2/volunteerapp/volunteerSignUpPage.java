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

public class volunteerSignUpPage extends AppCompatActivity implements View.OnClickListener {
    Button volunteerSignInButton;
    Button organizationSignInButton;
    Button volunteerSignUpButton;
    Button organizationSignUpButton;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.volunteer_sign_up);
        email =  findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        volunteerSignUpButton = findViewById(R.id.volunteerSignUpButton);

        volunteerSignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
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
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
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
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(volunteerSignUpPage.this, "registration successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
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
