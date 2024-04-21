package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.accidentdetectionalert.models.Ambulance;
import com.example.accidentdetectionalert.models.EmergencyContact;

import java.util.List;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<EmergencyContactsAdapter.ViewHolder>{
    private List<EmergencyContact> emergencyContactsList;
    private Context context;

    public EmergencyContactsAdapter(List<EmergencyContact> emergencyContactsList, Context context) {
        this.emergencyContactsList = emergencyContactsList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmergencyContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new EmergencyContactsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull EmergencyContactsAdapter.ViewHolder holder, int position) {
        EmergencyContact emergencyContact = emergencyContactsList.get(position);
        holder.userFullName.setText(emergencyContact.getFullName());
        holder.userPhoneNumber.setText(emergencyContact.getPhoneContact());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the selected item's ID to the fragment
                Fragment createEmergencyPage = new CreateEmergencyContact();
                Bundle args = new Bundle();
                args.putInt("emergencyContactId", emergencyContact.getId());
                createEmergencyPage.setArguments(args);

                FragmentTransaction fm = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.userFrameLayout, createEmergencyPage).addToBackStack(null).commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return emergencyContactsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userFullName;
        public TextView userPhoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userFullName = itemView.findViewById(R.id.userNameTextView_normal);
            userPhoneNumber = itemView.findViewById(R.id.userPhoneNumberTextView_normal);
        }
    }
}
