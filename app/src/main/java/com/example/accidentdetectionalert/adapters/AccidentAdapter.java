package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.models.Accident;

import java.util.List;

public class AccidentAdapter extends RecyclerView.Adapter<AccidentAdapter.ViewHolder>{
    private List<Accident> accidentList;
    private Context context;

    public AccidentAdapter(List<Accident> accidentList, Context context) {
        this.accidentList = accidentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accident_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accident accident = accidentList.get(position);
        holder.accidentIdTextView.setText("Accident ID: " + accident.getAccidentId());
        holder.dateTimeTextView.setText("Date Time: " + accident.getDateTime());
        holder.locationTextView.setText("Location: " + accident.getLocation());
        holder.statusTextView.setText("Status: " + accident.getStatus());
    }

    @Override
    public int getItemCount() {
        return accidentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accidentIdTextView;
        public TextView dateTimeTextView;
        public TextView locationTextView;
        public TextView statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accidentIdTextView = itemView.findViewById(R.id.accidentIdTextViewItem);
            dateTimeTextView = itemView.findViewById(R.id.accidentDateTimeTextViewItem);
            locationTextView = itemView.findViewById(R.id.accidentLocationTextViewItem);
            statusTextView = itemView.findViewById(R.id.accidentStatusTextViewItem);
        }
    }
}
