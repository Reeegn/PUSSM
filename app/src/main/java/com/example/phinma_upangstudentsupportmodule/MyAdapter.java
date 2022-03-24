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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.date.setText(user.getDate());
        holder.mental.setText(user.getMental() + "%");
        holder.physical.setText(user.getPhysical() + "%");
        holder.overall.setText(user.getOverall() + "%");
        holder.progress_mental.setProgress(Integer.parseInt(user.getMental()));
        holder.progress_physical.setProgress(Integer.parseInt(user.getPhysical()));
        holder.progress_overall.setProgress(Integer.parseInt(user.getOverall()));

    }

    @Override
    public int getItemCount() {
        return list.size();
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
