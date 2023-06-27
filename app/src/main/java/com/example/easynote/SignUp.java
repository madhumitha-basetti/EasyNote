package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easynote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    TextView login;
    Button registerUser;
    EditText txtname,txtemail,txtpassword,txtconpassword,txtphone;
    ProgressBar progressBar1;
    int counter=0;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        registerUser= (Button) findViewById(R.id.registerNowBtn);
        registerUser.setOnClickListener(this);

        txtname=(EditText)findViewById(R.id.fullName);
        txtname.setOnClickListener(this);
        txtemail=(EditText)findViewById(R.id.email);
        txtemail.setOnClickListener(this);
        txtpassword=(EditText)findViewById(R.id.password);
        txtpassword.setOnClickListener(this);
        txtconpassword=(EditText)findViewById(R.id.conpassword);
        txtconpassword.setOnClickListener(this);
        txtphone=(EditText)findViewById(R.id.phone);
        txtphone.setOnClickListener(this);

        login=(TextView)findViewById(R.id.loginNowBtn);
        login.setOnClickListener(this);
        progressBar1=findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerNowBtn:
                progressBar1.setVisibility(view.VISIBLE);
                Timer timer=new Timer();
                TimerTask timerTask=new TimerTask() {
                    @Override
                    public void run() {

                        counter++;
                        progressBar1.setProgress(counter);
                        if(counter==10){
                            timer.cancel();
                            registerUser();
                        }
                    }
                };
                timer.schedule(timerTask,100,100);

                break;

            case R.id.loginNowBtn:
                startActivity(new Intent(SignUp.this, Login.class));
                break;

        }

    }


    private void registerUser() {
        String mail=txtemail.getText().toString().trim();
        String fullname=txtname.getText().toString().trim();
        String password=txtpassword.getText().toString().trim();
        String password2=txtconpassword.getText().toString().trim();
        String mobile=txtphone.getText().toString().trim();

        if(fullname.isEmpty()){
            Toast.makeText(SignUp.this,"Full Name is required",Toast.LENGTH_SHORT).show();
            txtname.setError("Full Name is required");
            txtname.requestFocus();
            return;
        }

        if(mail.isEmpty()){
            txtemail.setError("Email is required");
            txtemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            txtpassword.setError("Password is required");
            txtpassword.requestFocus();
            return;
        }
        if(password2.isEmpty()){
            txtconpassword.setError("Confirmation Password is required");
            txtconpassword.requestFocus();
            return;
        }
        if(mobile.isEmpty()){
            txtphone.setError("Phone Number is required");
            txtphone.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            txtemail.setError("Please Provide valid email");
            txtemail.requestFocus();
            return;
        }
        if(password.length()<6){
            txtpassword.setError("Min password length should be 6 characters");
            txtpassword.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            txtconpassword.setError("Password doesn't match");
            txtconpassword.requestFocus();
            return;
        }
        FirebaseFirestore fStore=FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this,"User created",Toast.LENGTH_SHORT).show();
                        progressBar1.setVisibility(progressBar1.INVISIBLE);
                        Map<String,Object> user=new HashMap<>();
                        user.put("Name",fullname);
                        user.put("Email",mail);
                        user.put("Phone Number",mobile);

                        fStore.collection("users").document(fullname).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","onSuccess:user profile is created for "+fullname);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG","onFaillure:user profile is not created for "+fullname);
                            }
                        });

                    }
                    else{
                        Toast.makeText(SignUp.this,"Failed to register!",Toast.LENGTH_LONG).show();
                    }
                });
    }
}