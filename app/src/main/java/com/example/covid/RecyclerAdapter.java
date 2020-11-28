package com.example.covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ViewHolder> {

    Context context;

    public ArrayList <Hospital> hospitals;

    public RecyclerAdapter(Context context, ArrayList <Hospital> hospitals) {
        this.context = context;
        this.hospitals = hospitals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrecycler, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.hospital_name.setText(hospitals.get(position).getName());
        holder.location.setText(hospitals.get(position).getLocation());
        holder.general.setText(hospitals.get(position).getFreeGeneral());
        holder.oxygen.setText(hospitals.get(position).getFreeOxygen());
        holder.ventilator.setText(hospitals.get(position).getFreeVentilator());
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hospital_name;
        TextView location;
        TextView general;
        TextView oxygen;
        TextView ventilator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hospital_name = itemView.findViewById(R.id.item_text_name);
            location = itemView.findViewById(R.id.item_text_location);
            general = itemView.findViewById(R.id.item_text_general);
            oxygen = itemView.findViewById(R.id.item_text_oxygen);
            ventilator = itemView.findViewById(R.id.item_text_ventilator);
        }
    }
}
