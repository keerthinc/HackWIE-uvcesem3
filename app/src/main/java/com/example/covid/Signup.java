package com.example.covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    EditText signup_text_id;
    EditText signup_text_password;
    EditText signup_text_age;

    RadioButton signup_radio_male;
    RadioButton signup_radio_female;
    RadioButton signup_radio_other;

    CheckBox signup_checkbox_diabetes;
    CheckBox signup_checkbox_kidney;
    CheckBox signup_checkbox_heart;
    CheckBox signup_checkbox_respiratory;
    CheckBox signup_checkbox_pregnant;

    CheckBox signup_checkbox_aayushmann;
    CheckBox signup_checkbox_aarogya;
    CheckBox signup_checkbox_insurance;

    Button signup_button_update;

    FirebaseDatabase db;
    DatabaseReference ref;

    ValueEventListener valueEventListener;

    String id;
    String password;

    String age;
    String gender;

    boolean diabetes;
    boolean kidney;
    boolean heart;
    boolean respiratory;
    boolean pregnant;

    boolean aayushmaan;
    boolean aarogya;
    boolean insurance;

    Patient temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_text_id = findViewById(R.id.signup_text_id);
        signup_text_password = findViewById(R.id.signup_text_password);
        signup_text_age = findViewById(R.id.signup_text_age);

        signup_radio_male = findViewById(R.id.signup_radio_male);
        signup_radio_female = findViewById(R.id.signup_radio_female);
        signup_radio_other = findViewById(R.id.signup_radio_other);

        signup_checkbox_diabetes = findViewById(R.id.signup_checkbox_diabetes);
        signup_checkbox_heart = findViewById(R.id.signup_checkbox_heart);
        signup_checkbox_kidney = findViewById(R.id.signup_checkbox_kidney);
        signup_checkbox_respiratory = findViewById(R.id.signup_checkbox_respiratory);
        signup_checkbox_pregnant = findViewById(R.id.signup_checkbox_pregnant);

        signup_checkbox_aayushmann = findViewById(R.id.signup_checkbox_aayushmaan);
        signup_checkbox_aarogya = findViewById(R.id.signup_checkbox_aarogya);
        signup_checkbox_insurance = findViewById(R.id.signup_checkbox_insurance);

        signup_button_update = findViewById(R.id.signup_button_update);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Member");

        signup_radio_male.setChecked(true);
        signup_checkbox_pregnant.setEnabled(false);

        signup_radio_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_radio_female.setChecked(false);
                signup_radio_other.setChecked(false);
                signup_checkbox_pregnant.setChecked(false);
                signup_checkbox_pregnant.setEnabled(false);
            }
        });

        signup_radio_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_radio_male.setChecked(false);
                signup_radio_other.setChecked(false);
                signup_checkbox_pregnant.setEnabled(true);
            }
        });

        signup_radio_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_radio_male.setChecked(false);
                signup_radio_female.setChecked(false);
                signup_checkbox_pregnant.setChecked(false);
                signup_checkbox_pregnant.setEnabled(false);
            }
        });

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child(id).getChildrenCount() == 0L)
                        throw new IDDoesNotExistException();
                    if (!password.equals(String.valueOf(dataSnapshot.child(id).child("password").getValue())))
                        throw new PasswordDoesNotMatchException();
                    if (String.valueOf(dataSnapshot.child(id).child("updated").getValue()).equals("true"))
                        throw new DetailsHasBeenUpdatedException();

                    temp = new Patient(id, password);

                    temp.setAge(age);
                    temp.setGender(gender);

                    temp.setDiabetes(diabetes);
                    temp.setKidney(kidney);
                    temp.setHeart(heart);
                    temp.setRespiratory(respiratory);
                    temp.setPregnant(pregnant);

                    temp.setAayushmaan(aayushmaan);
                    temp.setAarogya(aarogya);
                    temp.setInsurance(insurance);

                    ref.child(id).setValue(temp);

                    Toast.makeText(Signup.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Signup.this, MainActivity.class));
                    finish();
                }
                catch (Exception e) {
                    Toast.makeText(Signup.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                }

                ref.removeEventListener(valueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        signup_button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = signup_text_id.getText().toString().toUpperCase();
                password = signup_text_password.getText().toString();

                age = signup_text_age.getText().toString();

                if (signup_radio_male.isChecked())
                    gender = "male";
                if (signup_radio_female.isChecked())
                    gender = "female";
                if (signup_radio_other.isChecked())
                    gender = "other";

                diabetes = signup_checkbox_diabetes.isChecked();
                kidney = signup_checkbox_kidney.isChecked();
                heart = signup_checkbox_heart.isChecked();
                respiratory = signup_checkbox_respiratory.isChecked();
                pregnant = signup_checkbox_pregnant.isChecked();

                aayushmaan = signup_checkbox_aayushmann.isChecked();
                aarogya = signup_checkbox_aarogya.isChecked();
                insurance = signup_checkbox_insurance.isChecked();

                ref.addValueEventListener(valueEventListener);
            }
        });
    }
}