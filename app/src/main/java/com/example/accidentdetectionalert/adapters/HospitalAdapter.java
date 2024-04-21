package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.fragments.CreateEmergencyContact;
import com.example.accidentdetectionalert.fragments.CreateHospital;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder>{
    private List<Hospital> hospitalList;
    private Context context;

    public HospitalAdapter(List<Hospital> hospitalList, Context context) {
        this.hospitalList = hospitalList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_update, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        Log.d("HospitalAdapter", "Hospital Name: " + hospital.getUser().getFullName());
        holder.hospitalNameTextView.setText(hospital.getUser().getFullName());
        holder.hospitalLocationTextView.setText(hospital.getLocation());
        holder.hospitalPhoneTextView.setText(hospital.getUser().getPhoneNumber());

        holder.updateButton.setOnClickListener(v -> {
            // Handle update button click here
            Toast.makeText(context, "Update clicked for " + hospital.getUser().getFullName(), Toast.LENGTH_SHORT).show();
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Handle delete button click here
            Toast.makeText(context, "Delete clicked for " + hospital.getUser().getFullName(), Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the selected item's ID to the fragment
                Fragment createHospitalPage = new CreateHospital();
                Bundle args = new Bundle();
                args.putInt("hospitalId", hospital.getUser().getUserId());
                createHospitalPage.setArguments(args);

                FragmentTransaction fm = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.adminFrameLayout, createHospitalPage).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hospitalNameTextView;
        public TextView hospitalLocationTextView;
        public TextView hospitalPhoneTextView;
        public Button updateButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalNameTextView = itemView.findViewById(R.id.userNameTextView_update);
            hospitalLocationTextView = itemView.findViewById(R.id.userLocationTextView_update);
            hospitalPhoneTextView = itemView.findViewById(R.id.userPhoneNumberTextView_update);
            updateButton = itemView.findViewById(R.id.updateUserButton_update);
            deleteButton = itemView.findViewById(R.id.deleteUserButton_update);
        }
    }
}
