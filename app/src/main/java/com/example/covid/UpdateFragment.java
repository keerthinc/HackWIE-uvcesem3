package com.example.covid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

public class UpdateFragment extends Fragment {

    TextView update_link_signup;

    EditText update_text_id;
    EditText update_text_password;
    EditText update_text_oxy;

    CheckBox update_checkbox_fever;
    CheckBox update_checkbox_breathlessness;
    CheckBox update_checkbox_pneumonia;

    Button update_button_submit;

    FirebaseDatabase db;
    DatabaseReference ref;

    String id;
    String password;
    String oxy;

    boolean fever;
    boolean breathlessness;
    boolean pneumonia;

    ValueEventListener valueEventListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        update_link_signup = view.findViewById(R.id.update_link_signup);

        update_text_id = view.findViewById(R.id.update_text_id);
        update_text_password = view.findViewById(R.id.update_text_password);
        update_text_oxy = view.findViewById(R.id.update_text_oxy);

        update_checkbox_fever = view.findViewById(R.id.update_checkbox_fever);
        update_checkbox_breathlessness = view.findViewById(R.id.update_checkbox_breathlessness);
        update_checkbox_pneumonia = view.findViewById(R.id.update_checkbox_pneumonia);

        update_button_submit = view.findViewById(R.id.update_button_submit);

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
                    if (String.valueOf(dataSnapshot.child(id).child("updated").getValue()).equals("false"))
                        throw new DetailsHasNotBeenUpdatedException();

                    ref.child(id).child("oxy").setValue(oxy);

                    ref.child(id).child("fever").setValue(String.valueOf(fever));
                    ref.child(id).child("breathlessness").setValue(String.valueOf(breathlessness));
                    ref.child(id).child("pneumonia").setValue(String.valueOf(pneumonia));

                    Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
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

        update_link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_checkbox_fever.setChecked(false);
                update_checkbox_breathlessness.setChecked(false);
                update_checkbox_pneumonia.setChecked(false);

                update_text_oxy.setText("");

                startActivity(new Intent(getActivity(), Signup.class));
                getActivity().finish();
            }
        });

        update_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = update_text_id.getText().toString().toUpperCase();
                password = update_text_password.getText().toString();

                oxy = update_text_oxy.getText().toString();

                fever = update_checkbox_fever.isChecked();
                breathlessness = update_checkbox_breathlessness.isChecked();
                pneumonia = update_checkbox_pneumonia.isChecked();

                ref.addValueEventListener(valueEventListener);
            }
        });

        return view;
    }
}