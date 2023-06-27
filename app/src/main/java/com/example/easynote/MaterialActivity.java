package com.example.easynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MaterialActivity extends AppCompatActivity {

    TextView Date,textView,head;
    CalendarView calendarView;
    Button submit;

    int paid,pending,total;
    String d,data="",type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        // head=findViewById(R.id.headings);
        Date=findViewById(R.id.date);
        submit=findViewById(R.id.submit_but);
        textView=findViewById(R.id.txt_view);
        calendarView=findViewById(R.id.cal_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                d=i2+"-"+(i1+1)+"-"+i;
                Date.setText(d);
                type=d+" material";
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data = "Name\t\t\t\tItem\t\t\t\tQuantity\t\t\tAmount\t\tStatus\n";
                paid=0;
                total=0;
                pending=0;
                //head.setText("Name\t\t\t\tItem\t\t\t\tQuantity\t\t\tAmount\t\t\tStatus\t\t\t");
                FirebaseFirestore.getInstance().collection(type).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException error) {

                        for (DocumentSnapshot snapshot : documentSnapshots) {


                                if (!documentSnapshots.isEmpty()) {
                                    data = data + "\t" + snapshot.getString("Name") + "\t\t" + snapshot.getString("Item") + "\t\t\t\t" + snapshot.getString("Quantity") + "\t\t\t\t" + snapshot.getString("Amount") + "\t\t\t\t\t" + snapshot.getString("Status") + "\n\n";
                                    if (snapshot.getString("Status").equals("Unpaid")) {
                                        pending = pending + Integer.parseInt(snapshot.getString("Amount"));
                                    } else if (snapshot.getString("Status").equals("Paid")) {
                                        paid = paid + Integer.parseInt(snapshot.getString("Amount"));
                                    }
                                    total = total + Integer.parseInt(snapshot.getString("Amount"));

                                }
                             else {
                                data = "";
                            }
                        }
                        if(total==0)
                        {
                            textView.setText("\n\n\n\n\n\t\t\t\t\t\t\tNo Data Found!");
                        }
                        else
                            textView.setText(data + "\n\n\tTotal Amount=  " + total + "\n\n\tPaid =  " + paid + "\n\n\tPending Amount =  " + pending + "\n\n");

                    }

                });
            }
        });

    }


}