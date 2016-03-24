package com.clover.seishun.hiandroid.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
//        Intent intent1 = new Intent(context, BroadcastReceiverActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // context.startActivity(intent1);
        // an Intent broadcast.
        Toast.makeText(context, "받았음!" + context, Toast.LENGTH_LONG).show();
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
