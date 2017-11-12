package com.zenithstudios.ezwakealarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {



        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();

        //This calls the service to start playing the ringtone
        Intent i = new Intent(context, AlarmRing.class);
        context.startService(i);

        /* NOTE: code for making the alarm ring
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        AudioManager mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, max, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        ringtone.play();
        */

        Intent intent1 = new Intent(context, PuzzleActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);





    }
}