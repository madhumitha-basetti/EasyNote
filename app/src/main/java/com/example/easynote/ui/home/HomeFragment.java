package com.example.easynote.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easynote.AccountsActivity;
import com.example.easynote.CalendarWagesDisplay;
import com.example.easynote.CustomerActivity;
import com.example.easynote.MaterialAccounts;
import com.example.easynote.MaterialActivity;
import com.example.easynote.R;
import com.example.easynote.WagesEntry;
import com.example.easynote.WorkersAccount;
import com.example.easynote.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button cal_cust,cust,cal_mat,mat,wage,fuel,cal_wage,workers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        cal_cust=root.findViewById(R.id.cal_cust_btn);
        cust=root.findViewById(R.id.cust_ac_btn);
        cal_mat=root.findViewById(R.id.cal_mat_btn);
        mat=root.findViewById(R.id.mat_ac_btn);
        wage=root.findViewById(R.id.wages_btn);
        cal_wage=root.findViewById(R.id.cal_fuel_wage_btn);
        workers=root.findViewById(R.id.wor_ac_btn);

        cal_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CustomerActivity.class);
                startActivity(intent);

            }
        });

        cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountsActivity.class);
                startActivity(intent);

            }
        });

        cal_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialActivity.class);
                startActivity(intent);

            }
        });

        mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MaterialAccounts.class);
                startActivity(intent);

            }
        });

        wage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WagesEntry.class);
                startActivity(intent);

            }
        });

        cal_wage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CalendarWagesDisplay.class);
                startActivity(intent);

            }
        });

        workers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WorkersAccount.class);
                startActivity(intent);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
