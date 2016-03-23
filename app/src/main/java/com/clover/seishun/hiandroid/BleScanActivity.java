package com.clover.seishun.hiandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BleScanActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_scan);

        textView = (TextView)findViewById(R.id.txtBLEContext);
        textView.setText(R.string.ble_context);

        ListView pairingList = (ListView)findViewById(R.id.pairing_list);
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnBLEStart:
                Toast.makeText(BleScanActivity.this, this + " BLE SCAN Start", Toast.LENGTH_LONG).show();
            case R.id.btnBLEStop:
                Toast.makeText(BleScanActivity.this, this + " BLE SCAN Stop", Toast.LENGTH_LONG).show();
        }
    }

/*
* */
//    private boolean getPairedDevice() {
//        if (mBluetoothAdapter.isEnabled()) {
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//            mDeviceContains = "";
//            if (pairedDevices.size() > 0) {
//                for (BluetoothDevice device : pairedDevices) {
//                    String getDeviceSerial = device.getName();
//                    String getAddr = device.getAddress();
//                    logger.debug("************deviceName = " + getDeviceSerial + " addr = " + getAddr);
//                    if (checkDevice(getDeviceSerial)) {
//                        pairedSetItemMap(getDeviceSerial, getAddr);
//                    }
//                }
//            }
//            availableSetItemMap();
//        } else {
//            madapter.addItem(getItemMap( getString(R.string.txt_bluetooth), getString(R.string.txt_bluetooth_available_dev), "", "", "", ""));
//            madapter.notifyDataSetChanged();
//        }
//        return true;
//    }
}
