package com.example.patchme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
//        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent resetPasswordActivity = new Intent(ForgetPassword.this, ResetPassword.class);
//                startActivity(resetPasswordActivity);
//                Toast.makeText(ForgetPassword.this, "Password reset initializing...", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}