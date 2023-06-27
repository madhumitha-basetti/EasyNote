package com.example.easynote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MaterialAccounts extends AppCompatActivity {

    TextView textView;
    Button submit;

    String data,Name;
    int pending,paid,total;
    EditText edtext;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_accounts);

        textView=findViewById(R.id.txt_view);
        edtext=(EditText)findViewById(R.id.name);
        submit=findViewById(R.id.submit_but);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtext.getText()!=null)
                {
                    Name=edtext.getText().toString().toUpperCase();
                }
                data="Date\t\t\t\t\t\t\tItem\t\t\t\tQuantity\t\t\tAmount\t\tStatus\n\n";
                paid=0;
                total=0;
                pending=0;

                //head.setText("Name\t\t\t\tItem\t\t\t\tQuantity\t\t\tAmount\t\t\tStatus\t\t\t");
                FirebaseFirestore.getInstance().collection(Name+" material").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException error) {

                        for (DocumentSnapshot snapshot : documentSnapshots) {

                            if (!documentSnapshots.isEmpty()) {
                                data=data+"\t"+snapshot.getString("Date") +"\t\t"+ snapshot.getString("Item") + "\t\t\t\t"+ snapshot.getString("Quantity") +"\t\t\t\t"+snapshot.getString("Amount") +"\t\t\t\t\t"+snapshot.getString("Status")+"\n\n";
                                if(snapshot.getString("Status").equals("Unpaid")){
                                    pending=pending+Integer.parseInt(snapshot.getString("Amount"));
                                }
                                else if(snapshot.getString("Status").equals("Paid"))
                                {
                                    paid=paid+Integer.parseInt(snapshot.getString("Amount"));
                                }
                                total=total+Integer.parseInt(snapshot.getString("Amount"));
                            }
                            else {
                                data="";
                            }
                        }
                        if(total==0)
                        {
                            textView.setText("\n\n\n\n\n\t\t\t\t\tMaterial Contractor Doesn't Exists!!");
                        }
                        else
                            textView.setText(data+"\n\n\tTotal Amount=  "+total+"\n\n\tPaid =  "+paid+"\n\n\tPending Amount=  "+pending+"\n\n");

                    }

                });

            }
        });

    }



}
