package com.example.accidentdetectionalert.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.models.HistoryAlert;
import com.example.accidentdetectionalert.models.User;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryAlert> historyAlertList;
    private Context context;

    public HistoryAdapter(List<HistoryAlert> historyAlertList, Context context) {
        this.historyAlertList = historyAlertList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryAlert historyAlert = historyAlertList.get(position);
        holder.alertDateTimeText.setText(historyAlert.getDateTime());
        holder.alertStatus.setText(historyAlert.getStatus());
    }

    @Override
    public int getItemCount() {
        return historyAlertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView alertDateTimeText;
        public TextView alertStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alertDateTimeText = itemView.findViewById(R.id.userNameTextView_normal);
            alertStatus = itemView.findViewById(R.id.userPhoneNumberTextView_normal);
        }
    }
}
