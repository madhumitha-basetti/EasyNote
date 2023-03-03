package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://signinregister-9ed58-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname=findViewById(R.id.fullname);
        final EditText email=findViewById(R.id.email);
        final EditText phone=findViewById(R.id.phone);
        final EditText password=findViewById(R.id.password);
        final EditText conpassword=findViewById(R.id.conpassword);

        final Button registerBtn=findViewById(R.id.registerBtn);
        final TextView loginNowBtn=findViewById(R.id.loginNowBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullnameTxt=fullname.getText().toString();
                final String emailTxt=email.getText().toString();
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conpasswordTxt=conpassword.getText().toString();

                //check if user fill all fields before sending data to firebase
                if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                   Toast.makeText(Register.this,"please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else if(!passwordTxt.equals(conpasswordTxt)){
                    Toast.makeText(Register.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                }
                else{

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phoneTxt)) {
                            Toast.makeText(Register.this, "Phone number is already registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                            databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);
                            Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error){

                    }
                });
                }

            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}