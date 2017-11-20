package com.zenithstudios.ezwakealarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AlarmPicker extends AppCompatActivity {

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Button button;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int hourOne, hourTwo, minuteOne, minuteTwo;
    //long time;

    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        hourOne = pref.getInt("AlarmHour1", 90);
        hourTwo = pref.getInt("AlarmHour2", 90);
        minuteOne = pref.getInt("AlarmMinute1", 90);
        minuteTwo = pref.getInt("AlarmMinute2", 90);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                */

                /*
                Intent intent2 = new Intent(AlarmPicker.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(AlarmPicker.this, 0, intent2, 0);
                */

                if(Build.VERSION.SDK_INT>22){
                    hour = alarmTimePicker.getHour();
                    minute = alarmTimePicker.getMinute();
                }
                else{
                    hour = alarmTimePicker.getCurrentHour();
                    minute = alarmTimePicker.getCurrentMinute();
                }



                /*time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));

                if(System.currentTimeMillis()>time)
                {
                    if (calendar.AM_PM == 0)
                        time = time + (1000*60*60*12);
                    else
                        time = time + (1000*60*60*24);
                }
                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
                */

                //Check for duplicate

                if((minute == minuteOne) && (hour == hourOne)){
                    Toast.makeText(AlarmPicker.this, "Sorry, you already have an identical alarm", Toast.LENGTH_SHORT).show();
                }

                else if ((minute == minuteTwo)&&(hour == hourTwo)){
                    Toast.makeText(AlarmPicker.this, "Sorry, you already have an identical alarm", Toast.LENGTH_SHORT).show();
                }

                //No duplicates
                else{
                    int count = pref.getInt("count", 0);

                    if (count > 2){
                        Toast.makeText(AlarmPicker.this, "Sorry, you already have 3 alarms", Toast.LENGTH_SHORT).show();
                    }
                    else if(count == 2){
                        count += 1;
                        editor.putInt("count", count);
                        editor.putInt("AlarmHour3", hour);
                        editor.putInt("AlarmMinute3", minute);
                        editor.putBoolean("toggleThree", false);
                        editor.apply();
                    }

                    else if(count == 1){
                        count += 1;
                        editor.putInt("count", count);
                        editor.putInt("AlarmHour2", hour);
                        editor.putInt("AlarmMinute2", minute);
                        editor.putBoolean("toggleTwo", false);
                        editor.apply();
                    }


                    else{
                        count += 1;
                        editor.putInt("count", count);
                        editor.putInt("AlarmHour1", hour);
                        editor.putInt("AlarmMinute1", minute);
                        editor.putBoolean("toggleOne", false);
                        editor.apply();
                    }
                }


                Intent goToMain = new Intent(AlarmPicker.this, MainActivity.class);
                startActivity(goToMain);







            }
        });


    }



    //public void OnToggleClicked(View view)
    //{
        /* NOTE: This is code for creating the alarm when timepicker is used
        if (((ToggleButton) view).isChecked())
        {
            Toast.makeText(AlarmPicker.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmPicker.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
        */

    //}

}