package com.example.amish_twitter.views.LandingPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.R;
import com.example.amish_twitter.views.Auth.WelcomeActivity;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private MaterialButton logoutBtn;
    private TextView welcomeText;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //casting views
        logoutBtn=findViewById(R.id.logoutBtn);
        welcomeText=findViewById(R.id.text);

        //init builder
        builder = new AlertDialog.Builder(this);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logoutDialogBox();
            }
        });
    }

    private void logoutUser(boolean bool){
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Get the SharedPreferences Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set the login status
        editor.putBoolean("isLoggedIn", bool);

        // Apply the changes
        editor.apply();

    }

    private void logoutDialogBox(){
        //logOut logic
        builder.setTitle("Log Out")
                .setMessage("Do you want to log out?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //logout
                        logoutUser(false);

                        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}