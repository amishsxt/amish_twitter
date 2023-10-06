package com.example.amish_twitter.views.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.Data.AppDatabase;
import com.example.amish_twitter.Data.User;
import com.example.amish_twitter.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity {

    private TextInputLayout passwordLayout, confirmLayout;
    private TextInputEditText password, confirm;
    private MaterialButton signupBtn;
    private ImageView backBtn;
    private ProgressBar progressBar;

    private AppDatabase appDatabase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //casting views
        password=findViewById(R.id.passwordEditText);
        confirm=findViewById(R.id.confirmPasswordEditText);
        passwordLayout=findViewById(R.id.passwordTextLayout);
        confirmLayout=findViewById(R.id.confirmPasswordTextLayout);
        backBtn=findViewById(R.id.backBtn);
        signupBtn=findViewById(R.id.signupBtn);
        progressBar=findViewById(R.id.progressBar);

        //init db
        user = (User) getIntent().getSerializableExtra("user");
        appDatabase = AppDatabase.getDB(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarStatus(true);

                String pass=password.getText().toString();
                String conf=confirm.getText().toString();

                if(pass.isBlank()){
                    passwordLayout.setError("Password can't be empty");
                }
                else if(!isValidPassword(pass)){
                    passwordLayout.setError("Password must have \nfirst letter as uppercase, \nminimum length of 8, "
                            + "\nand at least one special character \nand one number");
                }
                else if(conf.isBlank()
                || !(conf.equals(pass))){
                    confirmLayout.setError("Password doesn't match");
                }
                else{
                    user.setPassword(pass);
                    registerUser(user);

                    Intent intent = new Intent(PasswordActivity.this, WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    progressBarStatus(false);
                    startActivity(intent);

                }

                progressBarStatus(false);
            }
        });


    }

    private void registerUser(User user){
        appDatabase.userDao().insertRecord(user);
        Toast.makeText(this, "User Registered!", Toast.LENGTH_SHORT).show();
    }

    private void progressBarStatus(boolean bool){
        if(bool){
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            signupBtn.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isValidPassword(String password) {
        // Define the regular expression pattern
        String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$";

        // Compile the pattern
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(password);

        // Check if the password matches the pattern
        return matcher.matches();
    }
}