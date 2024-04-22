package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.fragments.Notification;
import com.example.accidentdetectionalert.models.Accident;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private List<Accident> accidentList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public NotificationAdapter(List<Accident> accidentList, Context context, SharedPreferences sharedPreferences) {
        this.accidentList = accidentList;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Accident accident = accidentList.get(position);
        holder.notificationText.setText(accident.getUser().getFullName() + " requires quick Medication!");

        String userRole = getUserRoleFromSharedPreferences();

        String[] statusOptions;
        if (userRole.equals("hospital")) {
            // Hospital role has options: None, Pickup, Admit
            statusOptions = new String[]{"None", "Pickup", "Admit"};
        } else if (userRole.equals("ambulance") || userRole.equals("police")) {
            // Ambulance role has options: None, Pickup, Dropped
            statusOptions = new String[]{"None", "Pickup", "Dropped"};
        } else {
            // Default options for other roles
            statusOptions = new String[]{"None"};
        }

        // Set up the spinner with the appropriate options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.notificationSpinner.setAdapter(adapter);

        holder.notificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = (String) parent.getItemAtPosition(position);
                Toast.makeText(context.getApplicationContext(), "Status: " + selectedStatus, Toast.LENGTH_SHORT).show();

                // Get the accident ID for the selected item
                Accident accident = accidentList.get(holder.getAdapterPosition());
                String accidentId = String.valueOf(accident.getAccidentId());

                // Update the status in the database
                updateAccidentStatus(accidentId, selectedStatus);
                // Update the status in the local list to reflect the change
                accident.setStatus(selectedStatus);
                // Notify the adapter that the data has changed
                notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private String getUserRoleFromSharedPreferences() {
        return sharedPreferences.getString("userRole", "");
    }
    private void updateAccidentStatus(String accidentId, String newStatus) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.updateAccidentStatus(accidentId, newStatus);
    }
    @Override
    public int getItemCount() {
        return accidentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationText;
        public Spinner notificationSpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.notificationText);
            notificationSpinner = itemView.findViewById(R.id.notificationSpinner);
        }
    }
}
