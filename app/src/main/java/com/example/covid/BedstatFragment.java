package com.example.covid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BedstatFragment extends Fragment {

    RecyclerAdapter recyclerAdapter;
    RecyclerView bedstat_recycler;

    ArrayList <Hospital> hospitals;

    Context context;

    FirebaseDatabase db;
    DatabaseReference ref;

    Hospital temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bedstat, container, false);
        bedstat_recycler = view.findViewById(R.id.bedstat_recycler);

        hospitals = new ArrayList<>();

        context = getContext();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Hospital");

        ref.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    hospitals.clear();

                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                        temp = new Hospital();

                        temp.setId(String.valueOf(dss.child("id").getValue()));
                        temp.setLocation(String.valueOf(dss.child("location").getValue()));
                        temp.setName(String.valueOf(dss.child("name").getValue()));

                        temp.setGeneral(String.valueOf(dss.child("general").getValue()));
                        temp.setOxygen(String.valueOf(dss.child("oxygen").getValue()));
                        temp.setVentilator(String.valueOf(dss.child("ventilator").getValue()));

                        temp.setGovernment(String.valueOf(dss.child("government").getValue()));

                        temp.setGeneral_occupied(String.valueOf(dss.child("general_occupied").getValue()));
                        temp.setOxygen_occupied(String.valueOf(dss.child("oxygen_occupied").getValue()));
                        temp.setVentilator_occupied(String.valueOf(dss.child("ventilator_occupied").getValue()));

                        hospitals.add(temp);
                    }

                    recyclerAdapter = new RecyclerAdapter(context, hospitals);
                    bedstat_recycler.setAdapter(recyclerAdapter);
                    bedstat_recycler.setLayoutManager(new LinearLayoutManager(context));
                }
                catch (Exception e) {
                    Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}