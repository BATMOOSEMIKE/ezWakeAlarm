package com.zenithstudios.ezwakealarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {

    int a, b,c,d,e,f, decider;
    Button nextButton;
    EditText answerBox, answerBox2;
    TextView question, question2, question3;
    Random random = new Random();
    String answer, answer2;
    RadioButton trueButton, falseButton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    long time1, time2, time3;

    int smallest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();

        time1 = pref.getLong("timeOne", 0);
        time2 = pref.getLong("timeTwo", 0);
        time3 = pref.getLong("timeThree", 0);

        smallest = 0;

        if(time1 != 0){
            if((time1 < time2)&& (time1<time3)){
                smallest = 1;
            }

            else if((time1 <time2)&&(time3 == 0)){
                smallest = 1;
            }

            else if((time1 < time3) && (time2 == 0)){
                smallest = 1;
            }

            else if((time2 == 0)&& (time3 == 0)){
                smallest = 1;
            }
        }

        else if(time2 != 0){
            if(time2<time3 || time3==0){
                smallest = 2;
            }
        }

        else if(time3!=0){
            smallest = 3;
        }

        if(smallest == 1){
            editor.putBoolean("toggleOne", false);
            editor.remove("timeOne");
        }
        else if(smallest == 2){
            editor.putBoolean("toggleTwo", false);
            editor.remove("timeTwo");
        }
        else if(smallest == 3){
            editor.putBoolean("toggleThree", false);
            editor.remove("timeThree");
        }

        editor.apply();



        nextButton = (Button)findViewById(R.id.nextButton);
        answerBox = (EditText)findViewById(R.id.answerBox);
        answerBox2 = (EditText)findViewById(R.id.answerBox2);
        question = (TextView)findViewById(R.id.question);
        question2 = (TextView)findViewById(R.id.question2);
        question3 = (TextView)findViewById(R.id.question3);

        trueButton = (RadioButton)findViewById(R.id.trueButton);
        falseButton = (RadioButton)findViewById(R.id.falseButton);



        a = random.nextInt(100)+1;
        b = random.nextInt(100)+1;
        c = random.nextInt(15)+1;
        d = random.nextInt(15)+1;

        //two numbers for true false game
        e = random.nextInt(75)+1;
        f = random.nextInt(75)+1;

        //setHideKeyboards
        answerBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeKeyboard(PuzzleActivity.this, answerBox.getWindowToken() );
                }
            }
        });

        answerBox2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeKeyboard(PuzzleActivity.this, answerBox2.getWindowToken() );
                }
            }
        });

        //two numbers for fake answer
        int no1 = random.nextInt(50)+50;
        int no2;
        if (no1>75){
            no2 = random.nextInt(50)+1;
        }
        else{
            no2 =  random.nextInt(100)+1;
        }

        int fakeAnswer = no1+no2;
        int realAnswer = e+f;

        //number to decide whether true or false
        decider = random.nextInt(2);
        switch(decider){
            case 0:
                question3.setText(e + " + "+f+" = "+fakeAnswer);
                break;
            case 1:
                question3.setText(e + " + "+f+" = "+ realAnswer);

        }



        question.setText(a + " + "+b+" = ?");
        question2.setText(c + " x "+d+" = ?");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = answerBox.getText().toString();
                String actualAnswer = Integer.toString(a+b);

                answer2 = answerBox2.getText().toString();
                String actualAnswer2 = Integer.toString(c*d);

                if(!answer.equals(actualAnswer)){
                    Toast.makeText(PuzzleActivity.this, "Sorry, first answer is incorrect", Toast.LENGTH_SHORT).show();
                }

                else if(!answer2.equals(actualAnswer2)){
                    Toast.makeText(PuzzleActivity.this, "Sorry, second answer is incorrect", Toast.LENGTH_SHORT).show();
                }

                else if((trueButton.isChecked() && decider == 0)||(falseButton.isChecked() && decider == 1 )|| (!(falseButton.isChecked()) && !(trueButton.isChecked()))){
                    Toast.makeText(PuzzleActivity.this, "Sorry, your true/false answer is incorrect", Toast.LENGTH_SHORT).show();

                }

                else{
                    Toast.makeText(PuzzleActivity.this, "You've successfully solved the puzzle!", Toast.LENGTH_SHORT).show();
                    Intent goBackToMain = new Intent(PuzzleActivity.this, MainActivity.class);
                    Intent i = new Intent(PuzzleActivity.this, AlarmRing.class);
                    stopService(i);
                    startActivity(goBackToMain);
                }


            }
        });
    }

    // This method hides the keyboard
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    @Override
    public void onBackPressed() {
        //Do nothing
    }

}