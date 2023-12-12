package com.example.patchme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    private Button confirmPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

//        confirmPasswordButton = findViewById(R.id.confirmPasswordButton);
//        confirmPasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent loginActivity = new Intent(ResetPassword.this, MainActivity.class);
//                startActivity(loginActivity);
//                Toast.makeText(ResetPassword.this, "Password reset was successful! Log in with your new password", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });

    }
}