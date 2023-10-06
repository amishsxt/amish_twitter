package com.example.amish_twitter.views.Auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amish_twitter.Data.AppDatabase;
import com.example.amish_twitter.Data.User;
import com.example.amish_twitter.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private MaterialButton nextBtn;
    private ImageView closeBtn;
    private DatePicker datePicker;
    private TextInputLayout nameLayout, usernameLayout, dobLayout;
    private TextInputEditText name, username, dob;
    private ProgressBar progressBar;

    private AppDatabase appDatabase;
    private List<User> list = new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //casting vies
        closeBtn=findViewById(R.id.backBtn);
        datePicker=findViewById(R.id.datePicker);
        name=findViewById(R.id.nameEditText);
        username=findViewById(R.id.usernameEditText);
        dob=findViewById(R.id.dobEditText);
        nameLayout=findViewById(R.id.nameTextLayout);
        usernameLayout=findViewById(R.id.usernameTextLayout);
        dobLayout=findViewById(R.id.dobTextLayout);
        nextBtn=findViewById(R.id.nextBtn);
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


        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dob.clearFocus();
                hideDatePicker();
                return false;
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==false){
                    hideSoftKeyboard(view);
                }
            }
        });

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dob.setFocusableInTouchMode(false);
                dob.clearFocus();
                hideDatePicker();
                return false;
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==false){
                    hideSoftKeyboard(view);
                }
            }
        });

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    dob.callOnClick();

                    hideSoftKeyboard(v);

                    return true;
                }

                return false;
            }
        });

        dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob.callOnClick();
            }
        });

        //dob
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
                // Set the focus programmatically
                dob.setFocusableInTouchMode(true);
                dob.requestFocus();
            }
        });

        // Get the current date and time using Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set the DatePicker to the current date
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                String dobText = dateString(d,m,y);
                dob.setText(dobText);
            }
        });

        //next
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarStatus(true);

                if(name.getText().toString().isBlank()){
                    nameLayout.setError("Please enter a name");
                }
                else if(checkUser(username.getText().toString())){
                    usernameLayout.setError("User already exists");
                }
                else if(username.getText().toString().isBlank()){
                    usernameLayout.setError("Please enter user detail");
                }
                else if (dob.getText().toString().isBlank()) {
                    dobLayout.setError("Please enter DOB");
                }
                else{
                    User user = new User(username.getText().toString(),name.getText().toString()
                            ,dob.getText().toString());

                    Intent intent = new Intent(SignUpActivity.this, PasswordActivity.class);
                    intent.putExtra("user",user);

                    progressBarStatus(false);
                    startActivity(intent);
                }

                progressBarStatus(false);
            }
        });

        //Removing the error messages
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dobLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private String dateString(int day, int month, int year){
        return day + " "  + getMonthFormat(month+1) + " " + year;
    }

    private String getMonthFormat(int month){
        String monthAbbreviation;

        switch (month) {
            case 1:
                monthAbbreviation = "JANUARY";
                break;
            case 2:
                monthAbbreviation = "FEBRUARY";
                break;
            case 3:
                monthAbbreviation = "MARCH";
                break;
            case 4:
                monthAbbreviation = "APRIL";
                break;
            case 5:
                monthAbbreviation = "MAY";
                break;
            case 6:
                monthAbbreviation = "JUNE";
                break;
            case 7:
                monthAbbreviation = "JULY";
                break;
            case 8:
                monthAbbreviation = "AUGUST";
                break;
            case 9:
                monthAbbreviation = "SEPTEMBER";
                break;
            case 10:
                monthAbbreviation = "OCTOBER";
                break;
            case 11:
                monthAbbreviation = "NOVEMBER";
                break;
            case 12:
                monthAbbreviation = "DECEMBER";
                break;
            default:
                monthAbbreviation = "JANUARY";
                break;
        }

        return monthAbbreviation;
    }

    private void showDatePicker(){
        datePicker.setVisibility(View.VISIBLE);

    }

    private void hideDatePicker(){
        datePicker.setVisibility(View.GONE);
    }

    private void hideSoftKeyboard(View view){
        // Get the InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Hide the keyboard
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private boolean checkUser(String s){
        for (User u : list){
            if(s.equals(u.getUsername())){
                user = u;
                return true;
            }
        }

        return false;
    }

}