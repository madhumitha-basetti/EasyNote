package com.example.easynote.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easynote.Customer;
import com.example.easynote.R;
import com.example.easynote.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    public RadioGroup rg;
    RadioButton rb;
    String d,type;



    List<String> itemList = new ArrayList<>();
    List<String> quanList=new ArrayList<>();
    ArrayList<Customer> customersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        buttonAdd = (Button) root.findViewById(R.id.button_add);
        buttonSubmitList = (Button) root.findViewById(R.id.button_submit_list);
        layoutList = (LinearLayout) root.findViewById(R.id.layout_list);

        rg = (RadioGroup) root.findViewById(R.id.radgroup);


        itemList.add(0,"Select Item");
        itemList.add("Sand");
        itemList.add("Bricks");
        itemList.add("Stones");
        itemList.add("Cement");
        quanList.add(0,"Select Quantity");
        quanList.add("1");
        quanList.add("2");
        quanList.add("3");
        quanList.add("4");
        quanList.add("5");
        quanList.add("6");

        // final TextView textView = binding.textDashboard;
        //  dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                d=i2+"-"+(i1+1)+"-"+i;
                binding.text.setText(d);
                type=d+" material";
                removeView();

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });
        buttonSubmitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkIfValidAndRead()) {
                    Toast.makeText(getContext(), "Succuss", Toast.LENGTH_LONG).show();
                }

            }
        });
        return root;


    }


    private boolean checkIfValidAndRead() {
        customersList.clear();
        boolean result = true;

        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View customerView = layoutList.getChildAt(i);
            EditText editCustomerName = (EditText) customerView.findViewById(R.id.edit_customer_name);
            EditText editAmount = (EditText) customerView.findViewById(R.id.edit_customer_amount);

            AppCompatSpinner spinnerItem = (AppCompatSpinner) customerView.findViewById(R.id.spinner_item);
            AppCompatSpinner spinnerQuan = (AppCompatSpinner) customerView.findViewById(R.id.spinner_Quantity);
            RadioGroup radg = customerView.findViewById(R.id.radgroup);
            int selectedId = radg.getCheckedRadioButtonId();
            RadioButton radb = (RadioButton) customerView.findViewById(selectedId);
            Customer customer = new Customer();

            if (!editCustomerName.getText().toString().equals("")) {
                customer.setCustomerName(editCustomerName.getText().toString().toUpperCase());
                //     Toast.makeText(getContext(),customer.getCustomerName(),Toast.LENGTH_SHORT).show();
            } else {
                result = false;
            }
            if (!editAmount.getText().toString().equals("")) {
                customer.setAmount(editAmount.getText().toString());
                //     Toast.makeText(getContext(),customer.getAmount(),Toast.LENGTH_SHORT).show();
            } else {
                result = false;
            }
            if (spinnerItem.getSelectedItemPosition() != 0) {
                customer.setItemName(itemList.get(spinnerItem.getSelectedItemPosition()));
                //      Toast.makeText(getContext(),customer.getItemName(),Toast.LENGTH_SHORT).show();


            } else {
                result = false;
            }
            if (spinnerQuan.getSelectedItemPosition() != 0) {
                customer.setQuantity(quanList.get(spinnerQuan.getSelectedItemPosition()));
                // Toast.makeText(getContext(),customer.getQuantity(),Toast.LENGTH_SHORT).show();

            } else {
                result = false;
            }

            if (!radb.getText().toString().equals("")) {
                if (radb.getText().toString().equals("Paid")) {
                    customer.setStatus("Paid");

                } else {
                    customer.setStatus("Unpaid");

                }
                if (customer.Status != null) {
                    //  Toast.makeText(getContext(),customer.Status,Toast.LENGTH_SHORT).show();
                }

            } else {
                result = false;
            }
            Toast.makeText(getContext(), customer.getCustomerName() + "-" + customer.getItemName() + "-" +
                    customer.getQuantity() + "-" + customer.getAmount() + "-" + customer.getStatus(), Toast.LENGTH_SHORT).show();

            HashMap<String, Object> hashMap = new HashMap<>();
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap.put("Name",customer.getCustomerName());
            hashMap.put("Item", customer.getItemName());
            hashMap.put("Quantity", customer.getQuantity());
            hashMap.put("Amount", customer.getAmount());
            hashMap.put("Status", customer.getStatus());
            hashMap2.put("Date",d);
            hashMap2.put("Item", customer.getItemName());
            hashMap2.put("Quantity", customer.getQuantity());
            hashMap2.put("Amount", customer.getAmount());
            hashMap2.put("Status", customer.getStatus());

            FirebaseFirestore.getInstance().collection(type).document().set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                      //      Toast.makeText(getContext(), "Added to Firebase", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to add to Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });

            FirebaseFirestore.getInstance().collection(customer.getCustomerName()+" material").document().set(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                   //         Toast.makeText(getContext(), "Added to Firebase", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to add to Firebase", Toast.LENGTH_SHORT).show();
                        }
                    });

            customersList.add(customer);
        }

        if(customersList.size()==0){
            result=false;
            Toast.makeText(getContext(),"Add Customers First",Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(getContext(),"Enter details Correctly",Toast.LENGTH_SHORT).show();
        }

        FirebaseFirestore.getInstance().collection(type).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot : documentSnapshots){
               //     Toast.makeText(getContext(), snapshot.getString("Name") +snapshot.getString("Item") +snapshot.getString("Quantity"),Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toast.makeText(getContext(), "Added to Firebase", Toast.LENGTH_SHORT).show();
        return result;

    }


    private void addView() {

        final View customerView = getLayoutInflater().inflate(R.layout.row_add_items,null,false);

        EditText editCustname = (EditText)customerView.findViewById(R.id.edit_customer_name);
        EditText editAmount=(EditText)customerView.findViewById(R.id.edit_customer_amount);
        AppCompatSpinner spinnerTeam = (AppCompatSpinner)customerView.findViewById(R.id.spinner_item);
        ArrayAdapter arrayAdapter = new ArrayAdapter(customerView.getContext(),android.R.layout.simple_spinner_item,itemList);
        spinnerTeam.setAdapter(arrayAdapter);

        AppCompatSpinner spinnerQuan = (AppCompatSpinner)customerView.findViewById(R.id.spinner_Quantity);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(customerView.getContext(),android.R.layout.simple_spinner_item,quanList);
        spinnerQuan.setAdapter(arrayAdapter2);

        rg=customerView.findViewById(R.id.radgroup);
        layoutList.addView(customerView);



    }


  /*  public void checkButton (View v){
        int radiobtnid=rg.getCheckedRadioButtonId();
        rb=(RadioButton) getActivity().findViewById(radiobtnid);
    }
    */


    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch(i){
                case R.id.radio_paid:
                    break;
                case R.id.radio_unpaid:
                    break;
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void removeView(){
        layoutList.removeAllViews();
    }
}
