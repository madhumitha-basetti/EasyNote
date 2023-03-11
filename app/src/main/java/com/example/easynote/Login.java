package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgot;
    private FirebaseAuth mAuth;
    private EditText txtemail, txtpassword,txtphone;
    private Button signin;
    private ProgressBar progressBar1;
    int counter=0;


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

        txtemail = (EditText)findViewById(R.id.email);
        txtpassword = (EditText)findViewById(R.id.password);
        txtphone=(EditText)findViewById(R.id.mobile);

        progressBar1=findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore dbroot;
        dbroot=FirebaseFirestore.getInstance();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerNowBtn:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.loginBtn:
                progressBar1.setVisibility(view.VISIBLE);
                Timer timer=new Timer();
                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar1.setProgress(counter);
                        if(counter==10){
                            timer.cancel();
                            userLogin();
                        }
                    }
                };
                timer.schedule(timerTask,100,100);
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(Login.this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = txtemail.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();
        String phone=txtphone.getText().toString().trim();
        if (email.isEmpty() && phone.isEmpty()) {
            txtemail.setError("Email or phone number is required!");
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


        if(!phone.isEmpty() && email.isEmpty()){

            FirebaseFirestore.getInstance().collection("users").whereEqualTo("Phone Number",phone).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){

                        for(QueryDocumentSnapshot document:task.getResult()){
                            String mail=document.get("Email").toString();
                            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(Login.this,SplashScreen.class));
                                        Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(Login.this,"Failed to Login!Please check your credentials",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }
                    }


                }
            });
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(Login.this,SplashScreen.class));
                        Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Login.this,"Failed to Login!Please check your credentials",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}