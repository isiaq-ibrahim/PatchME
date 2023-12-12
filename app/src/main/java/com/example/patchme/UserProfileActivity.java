package com.example.patchme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private TextView firstNameEditText, lastNameEditText, emailEditText, cityEditText, stateEditText, pickUpAddressEditText;
    private Button finishRegistration;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        emailEditText = findViewById(R.id.email);
        cityEditText = findViewById(R.id.city);
        stateEditText = findViewById(R.id.state);
        pickUpAddressEditText = findViewById(R.id.pickUpAddress);
        finishRegistration = findViewById(R.id.finishRegistration);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        finishRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishUserRegistration();
            }
        });
    }

    private void finishUserRegistration() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String state = stateEditText.getText().toString().trim();
        String pickUpAddress = pickUpAddressEditText.getText().toString().trim();

        if (firstName.isEmpty()){
            firstNameEditText.setText("First name field is empty!");
            firstNameEditText.requestFocus();

        }

        if (lastName.isEmpty()) {
            lastNameEditText.setText("Last name field is empty!");
            lastNameEditText.requestFocus();

        }

        if (email.isEmpty()) {
            emailEditText.setText("Email address field is empty!");
            emailEditText.requestFocus();

        }
        if (city.isEmpty()) {
            cityEditText.setText("City field is empty!");
            cityEditText.requestFocus();

        }

        if (state.isEmpty()) {
            stateEditText.setText("State field is empty!");
            stateEditText.requestFocus();
        }

        if (pickUpAddress.isEmpty()) {
            pickUpAddressEditText.setText("Pickup address is empty!");
            pickUpAddressEditText.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setText("Please enter a valid email address!");
            emailEditText.requestFocus();
        }

    }
}