package com.example.patchme;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

//    private EditText otpEditText;
//    private Button verifyOTPBtn;
//    private ProgressDialog pd;
//    private String mVerificationId;
//    private FirebaseAuth firebaseAuth;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
//    private static final String TAG = "MAIN_TAG";

    private EditText otpET;
    private Button verifyOTP;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpET = findViewById(R.id.otp);
        verifyOTP = findViewById(R.id.verifyOTP);
        mAuth = FirebaseAuth.getInstance();

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpET.getText().toString().trim();
                if (code.isEmpty()){
                    Toast.makeText(OTPVerificationActivity.this, "Please enter verification code...", Toast.LENGTH_SHORT).show();
                } else {
                    verifyOTP(mVerificationId, code);
                }
            }
        });

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(OTPVerificationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

//        pd = new ProgressDialog(this);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        otpEditText = findViewById(R.id.otp);
//        verifyOTPBtn = findViewById(R.id.verifyOTPBtn);
//
//        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkcode();
//            }
//        });
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Toast.makeText(OTPVerificationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                super.onCodeSent(verificationId, forceResendingToken);
//                Log.d(TAG, "onCodeSent: "+ verificationId);
//
//                mVerificationId = verificationId;
//                forceResendingToken = token;
//                pd.dismiss();
//            }
//        };
//
//    }
//
//    private void checkcode() {
//        String userEnteredOtp = phoneEnteredByUser.getText().toString();
//        if(userEnteredOtp.isEmpty() || userEnteredOtp.length()<6){
//            Toast.makeText(this, "Wrong Otp!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        finishEverything(userEnteredOtp);
//    }
//
//    private void finishEverything(String code) {
//        phoneEnteredByUser.setText(code);
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
//        sign_in(credential);
//    }
//
//    private void sign_in(PhoneAuthCredential credential) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.signInWithCredential(credential).addOnCompleteListener(OTPVerificationActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(OTPVerificationActivity.this, "User Signed in successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),ResetPassword.class));
//                }
//                else {
//                    Toast.makeText(OTPVerificationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//    private void verifyOTPCode() {
//        String otp = otpEditText.getText().toString().trim();
//        if (otp.isEmpty()){
//            otpEditText.setError("Please enter OTP code");
//            otpEditText.requestFocus();
//        } else {
//            verifyPhoneNumberWithCode(mVerificationId, otp);
//        }
//    }
//
//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        //pd.setMessage("Verifying OTP Code...");
//        //pd.show();
//
//        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        //signInWithPhoneAuthCredential(credential);
//    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        pd.setMessage("Phone number verification was successful, complete the profile registration to setup your account");
//        firebaseAuth.signInWithCredential(credential)
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        pd.dismiss();
//                        String phoneNumber = firebaseAuth.getCurrentUser().getPhoneNumber();
//                        Toast.makeText(OTPVerificationActivity.this, "Account registration successful!", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(OTPVerificationActivity.this, UserProfileActivity.class));
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(OTPVerificationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
    private void verifyOTP(String verificationId, String code) {
        pd.setMessage("Verifying Code");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging in...");
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pd.dismiss();
                        String phoneNumber = mAuth.getCurrentUser().getPhoneNumber();
                        Toast.makeText(OTPVerificationActivity.this, "Account successfully created for "+phoneNumber, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTPVerificationActivity.this, UserProfileActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(OTPVerificationActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}