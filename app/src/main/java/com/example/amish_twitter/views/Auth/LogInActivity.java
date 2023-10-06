package com.example.amish_twitter.views.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.Data.AppDatabase;
import com.example.amish_twitter.Data.User;
import com.example.amish_twitter.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputLayout usernameLayout;
    private MaterialButton nextBtn,forgotPasswordBtn;
    private ImageView closeBtn;
    private ProgressBar progressBar;
    private User user;

    private AppDatabase appDatabase;
    private List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //casting views
        username=findViewById(R.id.editText);
        usernameLayout=findViewById(R.id.editTextLayout);
        nextBtn=findViewById(R.id.nextBtn);
        forgotPasswordBtn=findViewById(R.id.forgotPasswordBtn);
        closeBtn=findViewById(R.id.backBtn);
        progressBar=findViewById(R.id.progressBar);

        //init db
        appDatabase = AppDatabase.getDB(this);
        list=appDatabase.userDao().getAllRecords();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBarStatus(true);

                if(checkUser(username.getText().toString())){
                    Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
                    intent.putExtra("user",user);

                    progressBarStatus(false);
                    startActivity(intent);
                }
                else{
                    usernameLayout.setError("Doesn't exists");
                    progressBarStatus(false);
                }

            }
        });

    }

    private boolean checkUser(String s){
        for (User u : list){
            if(s.equals(u.getUsername())){
                user = u;
                return true;
            }
        }

        return false;
    }

    private void progressBarStatus(boolean bool){
        if(bool){
            progressBar.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);
        }
    }


}