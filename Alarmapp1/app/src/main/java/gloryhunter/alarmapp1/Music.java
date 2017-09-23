package gloryhunter.alarmapp1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by SNOW on 9/18/2017.
 */

public class Music extends Service {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), QuestionActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Tra loi cau hoi de tat chuong :) " + "!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_play_for_work_black_24dp)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        String nhan = intent.getExtras().getString("extra");
        int volume = intent.getExtras().getInt("volume");


        if (nhan.equals("on")){
            id = 1;
        }else if(nhan.equals("off")){
            id = 0;
        }

        if (id == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.daumua);

            audioManager = (AudioManager) getSystemService(MainActivity.AUDIO_SERVICE);
            audioManager.setStreamVolume(audioManager.STREAM_MUSIC, volume, 0);

            mediaPlayer.start();

        }else if (id == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        mNM.notify(0, mNotify);

        return START_NOT_STICKY;
    }
}
