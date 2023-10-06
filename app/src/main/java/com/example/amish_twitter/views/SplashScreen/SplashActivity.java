package com.example.amish_twitter.views.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.R;
import com.example.amish_twitter.views.LandingPage.HomeActivity;
import com.example.amish_twitter.views.Auth.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //casting views
        logo=findViewById(R.id.logo);

        // Fade-in animation
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.  fade_in);
        fadeInAnimation.setDuration(800);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkLoginStatus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(fadeInAnimation);

    }

    private void checkLoginStatus(){
        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Retrieve the login status
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // Check the login status
        if (isLoggedIn) {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // The user is not logged in, perform appropriate actions
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}