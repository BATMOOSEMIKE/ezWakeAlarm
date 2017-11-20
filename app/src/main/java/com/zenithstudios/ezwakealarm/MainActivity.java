package com.zenithstudios.ezwakealarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
//OLD COLOR:<color name="colorPrimary">#3F51B5</color>

public class MainActivity extends AppCompatActivity {

    ImageButton plusButton;
    Switch switch1, switch2, switch3;
    TextView textView1, textView2, textView3, AMPM1, AMPM2, AMPM3;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int hour1, hour2, hour3, minute1, minute2, minute3, count;
    int toastHour, toastMinute;
    Calendar calendar;

    long time1, time2, time3, toastTime;

    boolean toggleOne, toggleTwo, toggleThree;

    CardView card1, card2, card3;

    AlarmManager alarmManager;
    PendingIntent pendingIntent, pendingIntent2, pendingIntent3;

    String hourOneDisplay, hourTwoDisplay, hourThreeDisplay, minuteOneDisplay, minuteTwoDisplay, minuteThreeDisplay, timeOneAMPM, timeTwoAMPM, timeThreeAMPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();

        calendar = Calendar.getInstance();

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        toggleOne = pref.getBoolean("toggleOne", false);
        toggleTwo = pref.getBoolean("toggleTwo", false);
        toggleThree = pref.getBoolean("toggleThree", false);

        textView1 = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        AMPM1 = (TextView)findViewById(R.id.textView4);
        AMPM2 = (TextView)findViewById(R.id.textView5);
        AMPM3 = (TextView)findViewById(R.id.textView6);

        switch1 = (Switch)findViewById(R.id.switch1);
        switch2 = (Switch)findViewById(R.id.switch2);
        switch3 = (Switch)findViewById(R.id.switch3);

        switch1.setChecked(toggleOne);
        switch2.setChecked(toggleTwo);
        switch3.setChecked(toggleThree);

        //90 and 69 are arbitrary error catching values
        hour1 = pref.getInt("AlarmHour1", 90);
        hour2 = pref.getInt("AlarmHour2",90);
        hour3 = pref.getInt("AlarmHour3", 90);
        minute1 = pref.getInt("AlarmMinute1", 69);
        minute2 = pref.getInt("AlarmMinute2", 69);
        minute3 = pref.getInt("AlarmMinute3", 69);

        card1 = (CardView)findViewById(R.id.card_view);
        card2 = (CardView)findViewById(R.id.card_view2);
        card3 = (CardView)findViewById(R.id.card_view3);

        count = pref.getInt("count",0);

        card1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(count !=0)

                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Are you sure you want to clear this alarm?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    count--;
                                    editor.clear();

                                    editor.putInt("count", count);

                                    if(toggleOne){
                                        alarmManager.cancel(pendingIntent);
                                    }

                                    if (hour2!=90)
                                    {
                                        editor.putInt("AlarmHour1", hour2);
                                        editor.putInt("AlarmMinute1", minute2);

                                        if(toggleTwo){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour2);
                                            calendar.set(Calendar.MINUTE, minute2);
                                            time1=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time1)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time1 += (1000*60*60*12);
                                                else
                                                    time1 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeOne", time1);


                                        }
                                    }

                                    if(hour3!=90)
                                    {
                                        editor.putInt("AlarmHour2", hour3);
                                        editor.putInt("AlarmMinute2", minute3);

                                        if(toggleThree){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour3);
                                            calendar.set(Calendar.MINUTE, minute3);
                                            time2=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time2)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time2 += (1000*60*60*12);
                                                else
                                                    time2 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeTwo", time2);
                                        }

                                    }

                                    if(toggleTwo){
                                        toggleOne = true;
                                    }
                                    else{
                                        toggleOne = false;
                                    }
                                    if(toggleThree){
                                        toggleTwo = true;
                                    }
                                    else{
                                        toggleTwo = false;
                                    }



                                    toggleThree = false;

                                    editor.putBoolean("toggleOne", toggleOne);
                                    editor.putBoolean("toggleTwo", toggleTwo);
                                    editor.putBoolean("toggleThree", toggleThree);



                                    editor.commit();

                                    startActivity(new Intent(MainActivity.this, MainActivity.class));


                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    return true;
                }

                return false;


            }
        });

        card2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(count>1){
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Are you sure you want to clear this alarm?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    count--;
                                    editor.clear();
                                    editor.commit();

                                    editor.putInt("count", count);

                                    if(toggleTwo){
                                        alarmManager.cancel(pendingIntent2);
                                    }

                                    //Reduntant check here (how can 1 be null when count is greater than 1) but I'll do it to be safe
                                    if(hour1!=90){
                                        editor.putInt("AlarmHour1", hour1);
                                        editor.putInt("AlarmMinute1", minute1);

                                        if(toggleOne){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour1);
                                            calendar.set(Calendar.MINUTE, minute1);
                                            time1=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time1)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time1 += (1000*60*60*12);
                                                else
                                                    time1 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeOne", time1);
                                        }
                                    }

                                    if(hour3!=90)
                                    {
                                        editor.putInt("AlarmHour2", hour3);
                                        editor.putInt("AlarmMinute2", minute3);

                                        if(toggleThree){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour3);
                                            calendar.set(Calendar.MINUTE, minute3);
                                            time2=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time2)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time2 += (1000*60*60*12);
                                                else
                                                    time2 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeTwo", time2);
                                        }
                                    }

                                    if(toggleThree){
                                        toggleTwo = true;
                                    }
                                    else{
                                        toggleTwo = false;
                                    }




                                    toggleThree = false;

                                    editor.putBoolean("toggleOne", toggleOne);
                                    editor.putBoolean("toggleTwo", toggleTwo);
                                    editor.putBoolean("toggleThree", toggleThree);

                                    editor.commit();

                                    startActivity(new Intent(MainActivity.this, MainActivity.class));


                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    return true;
                }

                return false;
            }
        });

        card3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(count>2)
                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Are you sure you want to clear this alarm?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    count--;
                                    editor.clear();
                                    editor.commit();

                                    editor.putInt("count", count);

                                    //Reduntant check here (how can 1 be null when count is greater than 1) but I'll do it to be safe
                                    if(hour1!=90){
                                        editor.putInt("AlarmHour1", hour1);
                                        editor.putInt("AlarmMinute1", minute1);

                                        if(toggleOne){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour1);
                                            calendar.set(Calendar.MINUTE, minute1);
                                            time1=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time1)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time1 += (1000*60*60*12);
                                                else
                                                    time1 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeOne", time1);
                                        }
                                    }

                                    if(hour2!=90)
                                    {
                                        editor.putInt("AlarmHour2", hour2);
                                        editor.putInt("AlarmMinute2", minute2);

                                        if(toggleTwo){
                                            calendar.set(Calendar.HOUR_OF_DAY, hour2);
                                            calendar.set(Calendar.MINUTE, minute2);
                                            time2=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                                            if(System.currentTimeMillis()>time2)
                                            {
                                                if (calendar.AM_PM == 0)
                                                    time2 += (1000*60*60*12);
                                                else
                                                    time2 += (1000*60*60*24);


                                            }

                                            editor.putLong("timeTwo", time2);
                                        }
                                    }

                                    if(toggleThree){
                                        alarmManager.cancel(pendingIntent3);
                                    }

                                    toggleThree = false;

                                    editor.putBoolean("toggleOne", toggleOne);
                                    editor.putBoolean("toggleTwo", toggleTwo);
                                    editor.putBoolean("toggleThree", toggleThree);

                                    editor.commit();

                                    startActivity(new Intent(MainActivity.this, MainActivity.class));


                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    return true;
                }


                return false;
            }
        });


        plusButton = (ImageButton)findViewById(R.id.plusbutton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Toast.makeText(MainActivity.this, "This app requires do not disturb permissions", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }

                else{
                    startActivity(new Intent(MainActivity.this, AlarmPicker.class));
                }

            }
        });



        //Set the hour display variables (checking for AM/PM as well)
        if (hour1!=90){
            if(hour1>12){
                hourOneDisplay = Integer.toString(hour1-12);
                timeOneAMPM = "PM";
            }
            else if(hour1 == 0){
                hourOneDisplay = Integer.toString(12);
                timeOneAMPM="AM";
            }
            else if(hour1 == 12){
                hourOneDisplay = Integer.toString(hour1);
                timeOneAMPM = "PM";
            }
            else{
                hourOneDisplay = Integer.toString(hour1);
                timeOneAMPM="AM";
            }
        }
        else{
            hourOneDisplay = null;
            timeOneAMPM = "";
        }

        if (hour2!=90){
            if(hour2>12){
                hourTwoDisplay = Integer.toString(hour2-12);
                timeTwoAMPM = "PM";
            }
            else if(hour2 == 0){
                hourTwoDisplay = Integer.toString(12);
                timeTwoAMPM = "AM";
            }
            else if(hour2 == 12){
                Integer.toString(hour2);
                timeOneAMPM = "PM";
            }
            else{
                hourTwoDisplay = Integer.toString(hour2);
                timeTwoAMPM="AM";
            }
        }
        else{
            hourTwoDisplay = null;
            timeTwoAMPM = "";
        }

        if (hour3!=90){
            if(hour3>12){
                hourThreeDisplay = Integer.toString(hour3-12);
                timeThreeAMPM = "PM";
            }
            else if(hour3 == 0){
                hourThreeDisplay = Integer.toString(12);
                timeThreeAMPM = "AM";
            }
            else if (hour3 == 12){
                hourThreeDisplay = Integer.toString(hour3);
                timeThreeAMPM = "PM";
            }
            else{
                hourThreeDisplay = Integer.toString(hour3);
                timeThreeAMPM="AM";
            }
        }
        else{
            hourThreeDisplay = null;
            timeThreeAMPM = "";
        }


        //Set the minute variables to be displayed
        if(minute1!=69){
            if(minute1<10){
                minuteOneDisplay = "0"+ Integer.toString(minute1);
            }
            else{
                minuteOneDisplay = Integer.toString(minute1);
            }

        }
        else{
            minuteOneDisplay="";
        }
        if(minute2!=69){
            if(minute2 <10){
                minuteTwoDisplay = "0" + Integer.toString(minute2);
            }
            else{
                minuteTwoDisplay = Integer.toString(minute2);
            }

        }
        else{
            minuteTwoDisplay="";
        }
        if(minute3!=69){
            if(minute3 < 10){
                minuteThreeDisplay = "0" + Integer.toString(minute3);
            }
            else{
                minuteThreeDisplay = Integer.toString(minute3);
            }

        }
        else{
            minuteThreeDisplay="";
        }


        if(hourOneDisplay!=null){
            textView1.setText(hourOneDisplay + ":" + minuteOneDisplay);
            AMPM1.setText(timeOneAMPM);
        }
        else{
            textView1.setTextSize(30);
            textView1.setText("No Alarm");
            AMPM1.setText("");
        }

        if(hourTwoDisplay!=null){
            textView2.setText(hourTwoDisplay + ":" + minuteTwoDisplay);
            AMPM2.setText(timeTwoAMPM);
        }
        else{
            textView2.setTextSize(30);
            textView2.setText("No Alarm");
            AMPM2.setText("");
        }

        if(hourThreeDisplay!=null){
            textView3.setText(hourThreeDisplay +":" + minuteThreeDisplay);
            AMPM3.setText(timeThreeAMPM);
        }
        else{
            textView3.setTextSize(30);
            textView3.setText("No Alarm");
            AMPM3.setText("");

        }

        //Create the intent for the alarm wakeup. IDs are weird numbers like 50 because apparently 1/2 etc are taken by default alarm app
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        pendingIntent2 = PendingIntent.getBroadcast(this, -1, intent, 0);
        pendingIntent3 = PendingIntent.getBroadcast(this, -2, intent, 0);



        //set listeners for when toggles are pressed
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    toggleOne = true;
                    editor.putBoolean("toggleOne", toggleOne);
                    editor.apply();

                    //Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour1);
                    calendar.set(Calendar.MINUTE, minute1);


                    //Important! getTimeInMillis returns time relative to the epoch (long time ago), not relative to beginning of the day or to the currentTime.

                    time1=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));

                    if(System.currentTimeMillis()>time1)
                    {
                        if (calendar.AM_PM == 0)
                            time1 += (1000*60*60*12);
                        else
                            time1 += (1000*60*60*24);

                    }

                    toastTime = time1-System.currentTimeMillis();

                    toastHour = (int)(toastTime/(1000*60*60));
                    toastMinute = (int)((toastTime - toastHour*1000*60*60)/(1000*60));

                    if(hour1 != 90){
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time1,pendingIntent);

                        editor.putLong("timeOne", time1);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "You have set the alarm for "+toastHour + " h " + "and " + toastMinute +" m from now", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(MainActivity.this, "There isn't an alarm here!", Toast.LENGTH_SHORT).show();
                    }



                }
                else{
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "You have disabled the alarm", Toast.LENGTH_SHORT).show();

                    toggleOne = false;
                    editor.putBoolean("toggleOne", toggleOne);
                    editor.remove("timeOne");

                    editor.apply();
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    toggleTwo = true;
                    editor.putBoolean("toggleTwo", toggleTwo);
                    editor.apply();

                    //Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour2);
                    calendar.set(Calendar.MINUTE, minute2);

                    time2=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                    if(System.currentTimeMillis()>time2)
                    {
                        if (calendar.AM_PM == 0)
                            time2 += (1000*60*60*12);
                        else
                            time2 += (1000*60*60*24);


                    }

                    toastTime = time2-System.currentTimeMillis();

                    toastHour = (int)(toastTime/(1000*60*60));
                    toastMinute = (int)((toastTime - toastHour*1000*60*60)/(1000*60));

                    if(hour2!=90){
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time2, pendingIntent2);

                        editor.putLong("timeTwo", time2);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "You have set the alarm for "+toastHour + " h " + "and " + toastMinute +" m from now", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "There isn't an alarm here!", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    toggleTwo = false;
                    editor.putBoolean("toggleTwo", toggleTwo);
                    editor.remove("timeTwo");
                    editor.apply();

                    alarmManager.cancel(pendingIntent2);
                    Toast.makeText(MainActivity.this, "You have disabled the alarm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    toggleThree = true;
                    editor.putBoolean("toggleThree", toggleThree);
                    editor.apply();

                    //Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour3);
                    calendar.set(Calendar.MINUTE, minute3);

                    time3=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                    if(System.currentTimeMillis()>time3)
                    {
                        if (calendar.AM_PM == 0)
                            time3 += (1000*60*60*12);
                        else
                            time3 += (1000*60*60*24);
                    }

                    toastTime = time3-System.currentTimeMillis();

                    toastHour = (int)(toastTime/(1000*60*60));
                    toastMinute = (int)((toastTime - toastHour*1000*60*60)/(1000*60));

                    if(hour3!=90){
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time3, pendingIntent3);

                        editor.putLong("timeThree", time3);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "You have set the alarm for "+toastHour + " h " + "and " + toastMinute +" m from now", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "There isn't an alarm here!", Toast.LENGTH_SHORT).show();
                    }


                }
                else{
                    toggleThree = false;
                    editor.putBoolean("toggleThree", toggleThree);
                    editor.remove("timeThree");
                    editor.apply();

                    alarmManager.cancel(pendingIntent3);
                    Toast.makeText(MainActivity.this, "You have disabled the alarm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: Add instructions for idiots
        //TODO: Make toast when asking for notifications



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_instructions) {
            Intent goToInstructions = new Intent(MainActivity.this, Instructions.class);
            startActivity(goToInstructions);
        }

        return super.onOptionsItemSelected(item);
    }


    /* Note: Well this is useless lol
    public int getHourForAlarm(float t){
        float milliseconds = t;
        Calendar rightNow = Calendar.getInstance();
        int currentHours = rightNow.get(Calendar.HOUR_OF_DAY);

        int hoursToAdd = (int) milliseconds/(1000*60*60);

        return currentHours+hoursToAdd;

    }

    public int getMinuteForAlarm(float t){
        float milliseconds = t;
        Calendar rightNow = Calendar.getInstance();
        int currentMinutes = rightNow.get(Calendar.MINUTE);
        int minutesToAdd = (int) (milliseconds%(1000*60*60))/(1000*60);

        return currentMinutes+minutesToAdd;

    }

    */


}