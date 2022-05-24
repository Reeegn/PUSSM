package com.example.phinma_upangstudentsupportmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    HashMap<String, HashMap<String, String>> data;

    public MyAdapter(Context context, HashMap<String, HashMap<String, String>> list) {
        this.context = context;
        this.data = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String key = (String) (data.keySet().toArray())[position];

        holder.date.setText(data.get(key).get("date"));
        holder.mental.setText(data.get(key).get("mental"));
        holder.physical.setText(data.get(key).get("physical"));
        holder.overall.setText(data.get(key).get("overall"));
        holder.progress_mental.setProgress(Integer.parseInt(data.get(key).get("mental")));
        holder.progress_physical.setProgress(Integer.parseInt(data.get(key).get("physical")));
        holder.progress_overall.setProgress(Integer.parseInt(data.get(key).get("overall")));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date, mental, physical, overall;
        ProgressBar progress_mental, progress_physical, progress_overall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            mental = itemView.findViewById(R.id.text_view_mental_history);
            physical = itemView.findViewById(R.id.text_view_physical_history);
            overall = itemView.findViewById(R.id.text_view_overall_history);
            progress_mental = itemView.findViewById(R.id.determinateBar_mental);
            progress_physical = itemView.findViewById(R.id.determinateBar_physical);
            progress_overall = itemView.findViewById(R.id.determinateBar_overall);

        }
    }
}
