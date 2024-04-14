package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.fragments.Notification;
import com.example.accidentdetectionalert.models.Accident;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private List<Accident> accidentList;
    private Context context;

    public NotificationAdapter(List<Accident> accidentList, Context context) {
        this.accidentList = accidentList;
        this.context = context;
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
        holder.notificationText.setText(accident.getUser().getFullName());

        // Update Notification Spinner from Picked -> Dropped
        // Hospital is Admit
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
