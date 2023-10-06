package com.example.amish_twitter.views.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.Data.User;
import com.example.amish_twitter.R;
import com.example.amish_twitter.views.LandingPage.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {

    private TextInputLayout usernameLayout, passwordLayout;
    private TextInputEditText username, password;
    private MaterialButton loginBtn;
    private ImageView closeBtn;
    private ProgressBar progressBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //casting views
        username=findViewById(R.id.usernameEditText);
        password=findViewById(R.id.passwordEditText);
        usernameLayout=findViewById(R.id.usernameTextLayout);
        passwordLayout=findViewById(R.id.passwordTextLayout);
        loginBtn=findViewById(R.id.loginBtn);
        closeBtn=findViewById(R.id.backBtn);
        progressBar=findViewById(R.id.progressBar);

        //init data
        user = (User) getIntent().getSerializableExtra("user");
        username.setText(user.getUsername());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarStatus(true);

                String p = password.getText().toString();

                if(p.isBlank()){
                    passwordLayout.setError("Please enter password");
                }
                else if(!(user.getPassword().equals(p))){
                    passwordLayout.setError("Incorrect password");
                }
                else{
                    loginUser(true);

                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user", user);

                    progressBarStatus(false);
                    startActivity(intent);
                }

                progressBarStatus(false);
            }
        });

        //Removing the error messages
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    private void loginUser(boolean bool){
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Get the SharedPreferences Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set the login status
        editor.putBoolean("isLoggedIn", bool);

        // Apply the changes
        editor.apply();

    }

    private void progressBarStatus(boolean bool){
        if(bool){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

}