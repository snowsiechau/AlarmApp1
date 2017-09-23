package gloryhunter.alarmapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by SNOW on 9/18/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{


    //trao doi du lieu tu nhieu ung dung voi nhau
    // khi den gio hen tren he thong se bao ve ung dung
    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi = intent.getExtras().getString("extra");
        int volume = intent.getExtras().getInt("volume");

        Intent intentAlarmReceiver = new Intent(context, Music.class);
        intentAlarmReceiver.putExtra("extra", chuoi);
        intentAlarmReceiver.putExtra("volume", volume);
        context.startService(intentAlarmReceiver);
    }
}
