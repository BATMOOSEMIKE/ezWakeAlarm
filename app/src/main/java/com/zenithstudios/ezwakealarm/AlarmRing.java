package com.zenithstudios.ezwakealarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmRing extends Service {

    static MediaPlayer player;
    static Ringtone ringtone;
    public AlarmRing() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {


        //For some reason neither of the following two lines work. They will play random songs that I used to use as alarm like Fallout Boy and Demi Lovato
        //Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Uri alarmUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        AudioManager mAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int highVolume = (int)0.8*max;
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, highVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();

        Timer mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!ringtone.isPlaying()) {
                    ringtone.play();
                }
            }
        }, 1000*1, 1000*1);

        /*
        player = MediaPlayer.create(this, alarmUri);
        player.setLooping(true);
        player.start();
        */

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy()
    {

        //player.stop();

        AudioManager finalAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        finalAudioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        ringtone.stop();
    }
}
