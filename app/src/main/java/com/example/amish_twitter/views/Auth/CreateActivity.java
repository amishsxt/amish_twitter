package com.example.amish_twitter.views.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.R;
import com.google.android.material.button.MaterialButton;

public class CreateActivity extends AppCompatActivity {

    private MaterialButton createBtn;
    private TextView loginHyperLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //casting views
        createBtn=findViewById(R.id.createAccountBtn);
        loginHyperLink=findViewById(R.id.loginBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginHyperLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}