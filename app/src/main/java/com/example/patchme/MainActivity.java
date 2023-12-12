package com.example.patchme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView forgetPasswordTextView, signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        forgetPasswordTextView = findViewById(R.id.forgetPasswordTextView);
        signUpTextView = findViewById(R.id.signUpTextView);

//        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent forgetPasswordActivity = new Intent(MainActivity.this, ForgetPassword.class);
//                startActivity(forgetPasswordActivity);
//            }
//        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}