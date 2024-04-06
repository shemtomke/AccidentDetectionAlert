package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
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
