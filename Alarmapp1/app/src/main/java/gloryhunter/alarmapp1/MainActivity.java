package gloryhunter.alarmapp1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart;
    Button btnStop;
    TextView tvTimePicker;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

    SeekBar sbVollume;
    AudioManager audioManager;

    int vollume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadUI();

        calendar = Calendar.getInstance();

        //cho phep truy cap va he thong bao dong
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        intent = new Intent(MainActivity.this, AlarmReceiver.class);

        controlVollume();

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

    }

    private void controlVollume() {
        audioManager = (AudioManager) getSystemService(MainActivity.AUDIO_SERVICE);
        sbVollume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVollume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        sbVollume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC, i, 0);
                vollume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void loadUI() {
        btnStart = (Button) findViewById(R.id.bt_start);
        btnStop = (Button) findViewById(R.id.bt_end);
        tvTimePicker = (TextView) findViewById(R.id.tv_timePicker);
        timePicker = (TimePicker) findViewById(R.id.timepicker);
        sbVollume = (SeekBar) findViewById(R.id.sb_vollume);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_start:
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String gio_sting = String.valueOf(gio);
                String phut_string = String.valueOf(phut);

                intent.putExtra("extra", "on");

                intent.putExtra("volume", vollume);

                //khac voi intent thuong la no se luon ton tai ke ca khi ung dung ket thuc
                pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this, 0, intent , PendingIntent.FLAG_UPDATE_CURRENT
                );

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                tvTimePicker.setText(gio_sting + " : " + phut_string);

                break;

            case R.id.bt_end:

                intent.putExtra("extra", "off");
                sendBroadcast(intent);
                //alarmManager.cancel(pendingIntent);
                break;

            default:
                break;
        }
    }
}
