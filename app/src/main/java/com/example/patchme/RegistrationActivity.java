package com.example.patchme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private TextView alreadyRegisteredTextView;
    private Button createAccountBtn;
    private EditText phoneNumberEditText, businessNameEditText, passwordEditText;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        The three inputs here are initialized
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        businessNameEditText = findViewById(R.id.businessName);
        passwordEditText = findViewById(R.id.password);

//        Firebase Authentication Initialization
        mAuth = FirebaseAuth.getInstance();

//        Progress bar shows when the button create account is clicked
        progressBar = findViewById(R.id.progressBar);

//        This will allow the user to move to the login screen
        alreadyRegisteredTextView = findViewById(R.id.alreadyRegisteredTextView);
        alreadyRegisteredTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

//        This will create an account for a new user when the button is clicked, first the OTP will be sent to the user and the information
//        inserted into the database i.e. phone number, business name and password
        createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                This method is called when the user clicks on the create account.
//                This verifies if the user has inputted a correct value for the fields
                registerUser();
            }
        });

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG, "onCodeSent: " + verificationId);

                mVerificationId = verificationId;
                forceResendingToken = token;
                pd.dismiss();
            }
        };
    }
    private void registerUser() {
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String businessName = businessNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (phoneNumber.isEmpty()){
            phoneNumberEditText.setError("Phone number is empty!");
            phoneNumberEditText.requestFocus();
            return;
        } else if (businessName.isEmpty()){
            businessNameEditText.setError("Business name is empty!");
            businessNameEditText.requestFocus();
            return;
        } else if (password.isEmpty()){
            passwordEditText.setError("Password is empty!");
            passwordEditText.requestFocus();
            return;
        }else if (password.length() < 6){
            passwordEditText.setError("Minimum password length is six characters!");
            passwordEditText.requestFocus();
            return;
        }else if(phoneNumber.length() < 10 ){
            phoneNumberEditText.setError("Please enter a valid phone");
            phoneNumberEditText.requestFocus();
            return;
        }else {
            startPhoneNumberVerification(phoneNumber);
            startActivity(new Intent(RegistrationActivity.this, OTPVerificationActivity.class));
        }

//        The progress bar is displayed when the user clicks on create account
        progressBar.setVisibility(View.VISIBLE);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Account registration successful!");
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pd.dismiss();
                        String phoneNumber = mAuth.getCurrentUser().getPhoneNumber();
                        Toast.makeText(RegistrationActivity.this, "Account successfully created for "+phoneNumber, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, OTPVerificationActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RegistrationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}