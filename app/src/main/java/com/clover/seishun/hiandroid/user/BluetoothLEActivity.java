package com.clover.seishun.hiandroid.user;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.util.HashSet;
import java.util.Set;

public class BluetoothLEActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;

    TextView textView;
    private HashSet<String> mArrayAdapter;

    //stop scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    ListView pairingList = (ListView)findViewById(R.id.paired_devices);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_le);

        textView = (TextView)findViewById(R.id.txtBLEContext);
        textView.setText(R.string.ble_context);
    }

    private void bluetoothLowEnergy() {
        settingUpBluetooth();
        findingBLEDevices();
        connectingDevices();
        managingConnection();
        workingProfiles();
    }

    private void workingProfiles() {

    }

    private void managingConnection() {

    }

    private void connectingDevices() {
//        connectingServer();
//        ConnectingClient();


    }

    private void findingBLEDevices() {
//        queryingPairedDevices();
//        discoveringDevices();

//        startLEScan();
//        getPairedDevice();
//        if(mArrayAdapter.size()>0){
//            pairingList.setAdapter((ListAdapter) mArrayAdapter);
//        }
    }

    private void settingUpBluetooth() {
        //1. Get the Bluetooth Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

//        if (mBluetoothAdapter == null || ) {
//            //ble : // Checks if Bluetooth is supported on the device.
//            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        }


        /**
         * A dialog will appear requesting user permission to enable Bluetooth, as shown in Figure 1.
         * If the user responds "Yes," the system will begin to enable Bluetooth and focus will return to your application
         * once the process completes (or fails).
         The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0),
         that the system passes back to you in your onActivityResult() implementation as the requestCode parameter.
         If enabling Bluetooth succeeds, your activity receives the RESULT_OK result code in the onActivityResult() callback.
         If Bluetooth was not enabled due to an error (or the user responded "No") then the result code is RESULT_CANCELED.
         */
    }


    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnBLEStart:
                Toast.makeText(BluetoothLEActivity.this, this + " BLE SCAN Start", Toast.LENGTH_LONG).show();
                bluetoothLowEnergy();
                break;
            case R.id.btnBLEStop:
                Toast.makeText(BluetoothLEActivity.this, this + " BLE SCAN Stop", Toast.LENGTH_LONG).show();
                stopLEScan();
                break;
        }
    }

    private void stopLEScan() {

    }


    private void getPairedDevice() {
        if (mBluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            //If there are paired devices
            if (pairedDevices.size() > 0) {
                mArrayAdapter.clear();
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListViewActivity
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }else{
                mArrayAdapter.add("No device");
            }
        }
    }
}
