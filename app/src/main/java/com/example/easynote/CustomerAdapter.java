package com.example.easynote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easynote.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerView> {

    ArrayList<Customer> customersList = new ArrayList<>();

    public CustomerAdapter(ArrayList<Customer> customerList) {
        this.customersList = customerList;
    }

    @NonNull
    @Override
    public CustomerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new CustomerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerView holder, int position) {

        Customer customer=customersList.get(position);
        holder.textCustomerName.setText(customer.getCustomerName());
        holder.textQuantity.setText(customer.getQuantity());
        holder.textAmount.setText(customer.getAmount());
        holder.textItem.setText(customer.getItemName());
        holder.textStatus.setText(customer.getStatus());
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public class CustomerView extends RecyclerView.ViewHolder{


        TextView textCustomerName,textItem,textQuantity,textAmount,textStatus;
        public CustomerView(@NonNull View itemView){
            super(itemView);

            textCustomerName=(TextView)itemView.findViewById(R.id.tvName);
            textItem=(TextView)itemView.findViewById(R.id.tvItem);
            textQuantity=(TextView)itemView.findViewById(R.id.tvQuantity);
            textAmount=(TextView)itemView.findViewById(R.id.tvAmount);
            textStatus=(TextView)itemView.findViewById(R.id.tvStatus);
        }
    }
}

