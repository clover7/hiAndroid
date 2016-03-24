package com.clover.seishun.hiandroid.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import org.joda.time.DateTime;

public class BroadcastReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
//        btnAlertOnTime
//        btnAlertRepeat
//          btnAlertStop
        init();

    }

    private void init() {
        TextView title_br = (TextView)findViewById(R.id.title_br_alert);
        title_br.setText(R.string.alarm_text);

        TextView title_br_detectFree = (TextView)findViewById(R.id.title_br_detectFree);
        title_br_detectFree.setText(R.string.br_detectFree);

        Button btnDetectFree = (Button) findViewById(R.id.btnDetectFree);
        btnDetectFree.setText("발견");

        Button btnAlertOnTime = (Button) findViewById(R.id.btnAlertOnTime);
        btnAlertOnTime.setText("OnTime");
        Button btnAlertRepeat = (Button) findViewById(R.id.btnAlertRepeat);
        btnAlertRepeat.setText("Repeat");
        Button btnAlertStop = (Button) findViewById(R.id.btnAlertStop);
        btnAlertStop.setText("Stop");
    }

    public void mOnClick(View v){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent sender;

        switch(v.getId()){
            case R.id.btnDetectFree:
                intent = new Intent();
                intent.setAction("andexam.ver4_1.FREEWIFI");
                sendBroadcast(intent);
                break;
            case R.id.btnAlertOnTime:
                intent = new Intent(this, AlarmReceiver.class);
                sender = PendingIntent.getBroadcast(this, 0, intent, 0);

                DateTime now = new DateTime();
                alarmManager.set(AlarmManager.RTC, now.plusSeconds(3).getMillis(), sender);
                break;
            case R.id.btnAlertRepeat:
                break;
            case R.id.btnAlertStop:
                intent = new Intent(this, AlarmReceiver.class);
                sender = PendingIntent.getBroadcast(this, 0, intent, 0);

                if(v.getId() == R.id.btnAlertRepeat){
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                            SystemClock.elapsedRealtime(), 50000, sender);
                }else{
                    alarmManager.cancel(sender);
                }
                break;
        }
    }

    class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"It's time to start.", Toast.LENGTH_LONG).show();
        }
    }


}

