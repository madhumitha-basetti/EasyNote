package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easynote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText txtemail;
    private Button txtreset,txtlogin;

    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txtemail=(EditText)findViewById(R.id.email);
        txtreset=(Button)findViewById(R.id.reset);
        txtlogin=(Button)findViewById(R.id.loginBtn);

        auth=FirebaseAuth.getInstance();

        txtreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();

            }

            private void resetPassword() {
                String mail=txtemail.getText().toString().trim();

                if(mail.isEmpty()){
                    txtemail.setError("Email is required!");
                    txtemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    txtemail.setError("Please provide valid email!");
                    txtemail.requestFocus();
                    return;
                }
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ForgotPassword.this,"Try Again! Something happened!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,Login.class));
            }
        });
    }
}