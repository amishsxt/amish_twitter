package com.example.amish_twitter.views.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.R;
import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity {

    private MaterialButton loginBtn;
    private TextView signupHyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //casting views
        loginBtn=findViewById(R.id.loginBtn);
        signupHyperlink=findViewById(R.id.signupBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        signupHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
    }
}