package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgot;
    private FirebaseAuth mAuth;
    private EditText txtemail, txtpassword;
    private Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.registerNowBtn);
        register.setOnClickListener((this));

        forgot = (TextView) findViewById(R.id.forgetpassword);
        forgot.setOnClickListener((this));

        signin = (Button) findViewById(R.id.loginBtn);
        signin.setOnClickListener(this);

        txtemail = (EditText)findViewById(R.id.phone_email);
        txtpassword = (EditText)findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerNowBtn:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = txtemail.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();

        if (email.isEmpty()) {
            txtemail.setError("Email is required!");
            txtemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtemail.setError("Please enter a valid email!");
            txtemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            txtpassword.setError("password is required!");
            txtpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            txtpassword.setError("Min password length should be 6 characters");
            txtpassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
                else{
                    Toast.makeText(Login.this,"Failed to Login!Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}