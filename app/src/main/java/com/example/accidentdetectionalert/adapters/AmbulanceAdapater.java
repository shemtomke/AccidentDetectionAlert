package com.example.accidentdetectionalert.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.fragments.CreateAmbulance;
import com.example.accidentdetectionalert.fragments.CreateHospital;
import com.example.accidentdetectionalert.models.Ambulance;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;
public class AmbulanceAdapater extends RecyclerView.Adapter<AmbulanceAdapater.ViewHolder>{
    private List<Ambulance> ambulanceList;
    private Context context;

    public AmbulanceAdapater(List<Ambulance> ambulanceList, Context context) {
        this.ambulanceList = ambulanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public AmbulanceAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_update, parent, false);
        return new AmbulanceAdapater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceAdapater.ViewHolder holder, int position) {
        Ambulance ambulance = ambulanceList.get(position);
        holder.ambulanceNameTextView.setText(ambulance.getUser().getFullName());
        holder.ambulanceLocationTextView.setText(ambulance.getLocation());
        holder.ambulancePhoneTextView.setText(ambulance.getUser().getPhoneNumber());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Pass the selected item's ID to the fragment
//                Fragment createAmbulance = new CreateAmbulance();
//                Bundle args = new Bundle();
//                args.putInt("ambulanceId", ambulance.getUser().getUserId());
//                createAmbulance.setArguments(args);
//
//                FragmentTransaction fm = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
//                fm.replace(R.id.adminFrameLayout, createAmbulance).addToBackStack(null).commit();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return ambulanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ambulanceNameTextView;
        public TextView ambulanceLocationTextView;
        public TextView ambulancePhoneTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ambulanceNameTextView = itemView.findViewById(R.id.userNameTextView_update);
            ambulanceLocationTextView = itemView.findViewById(R.id.userLocationTextView_update);
            ambulancePhoneTextView = itemView.findViewById(R.id.userPhoneNumberTextView_update);
        }
    }
}
