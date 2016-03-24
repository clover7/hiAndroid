package com.clover.seishun.hiandroid.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastCustomReceiver extends BroadcastReceiver {
    public BroadcastCustomReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastCustomReceiver is receiving
//        Intent intent1 = new Intent(context, BroadcastReceiverActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // context.startActivity(intent1);
        // an Intent broadcast.
        String msg = "받았음!" + context + "::" + intent.getAction();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
