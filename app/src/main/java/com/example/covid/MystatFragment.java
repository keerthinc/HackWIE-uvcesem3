package com.example.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class MystatFragment extends Fragment {

    EditText mystat_text_id;
    EditText mystat_text_password;

    TextView mystat_text_status;

    Button mystat_button_submit;

    FirebaseDatabase db;
    DatabaseReference ref;

    ValueEventListener valueEventListener;

    String id;
    String password;
    String status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mystat, container, false);

        mystat_text_id = view.findViewById(R.id.mystat_text_id);
        mystat_text_password = view.findViewById(R.id.mystat_text_password);

        mystat_text_status = view.findViewById(R.id.mystat_text_status);

        mystat_button_submit = view.findViewById(R.id.mystat_button_submit);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Member");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child(id).getChildrenCount() == 0L)
                        throw new IDDoesNotExistException();
                    if (!password.equals(String.valueOf(dataSnapshot.child(id).child("password").getValue())))
                        throw new PasswordDoesNotMatchException();

                    if (String.valueOf(dataSnapshot.child(id).child("home_isolation").getValue()).equals("true"))
                        status = "Home isolation";
                    else
                        status = String.valueOf(dataSnapshot.child(id).child("status").getValue());

                    mystat_text_status.setText(status);
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                }

                ref.removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mystat_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mystat_text_id.getText().toString().toUpperCase();
                password = mystat_text_password.getText().toString();

                ref.addValueEventListener(valueEventListener);
            }
        });

        return view;
    }
}