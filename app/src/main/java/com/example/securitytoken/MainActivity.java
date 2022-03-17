package com.example.securitytoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    static String PREF_NAME = "SharedPrefFile";
    static SharedPreferences SavedTimeStamp;
    static SharedPreferences.Editor editor;
    long timeUntilFinished;
    static String TIMESTAMP_KEY = "TIMESTAMP_KEY";
    private CountDownTimer mCountDownTimer;
    static String currentDateTime;
    static ArrayList<String> arrayIntegerList = new ArrayList<String>();
    private EditText countDownTextView;
    TextView passCodeTextView;
    static int passCode;
    Button verifyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shared Preference
        SavedTimeStamp = getSharedPreferences(PREF_NAME,0);
        editor = SavedTimeStamp.edit();

        setContentView(R.layout.activity_main);

        passCodeTextView= findViewById(R.id.editText);
        countDownTextView = findViewById(R.id.editText2);
        verifyButton = (Button)findViewById(R.id.button1);


        if(Verification.isSaved==true)
        {
            //Verification.isSaved=false;
            Verification.restoreTimeStamp();
            openSecondaryActivity();
        }
        createPassCode();

        new CountDownTimer(60000, 1000){
            @Override
            public void onTick(long timeUntilFinished) {
                countDownTextView.setText("seconds remaining: " + timeUntilFinished / 1000 % 60);
            }

            @Override
            public void onFinish() {
                createPassCode();
                start();
            }
        }.start();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                    if(Verification.isSaved==true)
//                    {
//                        Verification.restoreTimeStamp();
//                    }
                openSecondaryActivity();
            }
        });
    }

    public void createPassCode() {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        passCode=minute*1245 + 10000;
        String timeStamp = getTimeStamp();
        arrayIntegerList.add(timeStamp);
        String passCodeStr = Integer.toString(passCode);
        passCodeTextView.setText(passCodeStr);
    }

    public void openSecondaryActivity() {
        Intent intent = new Intent(this, Verification.class);
        startActivity(intent);
    }

//    public static int getPassCode()
//    {
//        return passCode;
//    }

    public static String getTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateTime = dateFormat.format(new Date());
        return currentDateTime;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timeLeft", timeUntilFinished);

        EditText passCodeTextView = (EditText) findViewById(R.id.editText);
        CharSequence writtenData = passCodeTextView.getText();
        outState.putCharSequence("MySavedData", writtenData);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence storeData = savedInstanceState.getCharSequence("MySavedData");
        EditText myEditText = (EditText)findViewById(R.id.editText);
        myEditText.setText(storeData);

        timeUntilFinished = savedInstanceState.getLong("timeLeft");

    }

}