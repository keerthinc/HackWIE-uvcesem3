package com.example.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    TextView home_text_emptybed;
    TextView home_text_occupiedbed;
    TextView home_text_confirmed;
    TextView home_text_active;
    TextView home_text_recovered;
    TextView home_text_deaths;

    FirebaseDatabase db;
    DatabaseReference ref;

    String active;
    String confirmed;
    String deaths;
    String recovered;

    String occupied;
    String available;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_text_emptybed = view.findViewById(R.id.home_text_emptybed);
        home_text_occupiedbed = view.findViewById(R.id.home_text_occupiedbed);
        home_text_confirmed = view.findViewById(R.id.home_text_confirmed);
        home_text_active = view.findViewById(R.id.home_text_active);
        home_text_recovered = view.findViewById(R.id.home_text_recovered);
        home_text_deaths = view.findViewById(R.id.home_text_deaths);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Tracker");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    active = String.valueOf(dataSnapshot.child("Active").getValue());
                    recovered = String.valueOf(dataSnapshot.child("Recovered").getValue());
                    confirmed = String.valueOf(dataSnapshot.child("Confirmed").getValue());
                    deaths = String.valueOf(dataSnapshot.child("Deaths").getValue());

                    occupied = String.valueOf(dataSnapshot.child("Occupied").getValue());
                    available = String.valueOf(dataSnapshot.child("Available").getValue());

                    home_text_active.setText(active);
                    home_text_confirmed.setText(confirmed);
                    home_text_deaths.setText(deaths);
                    home_text_recovered.setText(recovered);

                    home_text_emptybed.setText(available);
                    home_text_occupiedbed.setText(occupied);
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}