package com.clover.seishun.hiandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;

    BluetoothAdapter mBluetoothAdapter;
    TextView textView;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<CharSequence> mStoredDeviceArrayAdapter;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        textView = (TextView)findViewById(R.id.txtBLEContext);
        textView.setText("BLUETOOTH");

        mStoredDeviceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.devices, android.R.layout.simple_list_item_1);

        ListView storedDevicesListView = (ListView)findViewById(R.id.stored_devices);
        storedDevicesListView.setAdapter(mStoredDeviceArrayAdapter);
        storedDevicesListView.setOnItemClickListener(mDeviceClickListener);
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
        Toast.makeText(this, "findBLEDevices", Toast.LENGTH_LONG).show();

        queryingPairedDevices();

        // Find and set up the ListViewActivity for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListViewActivity for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
//        // Register for broadcasts when a device is discovered
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        this.registerReceiver(mReceiver, filter);
//
//        // Register for broadcasts when discovery has finished
//        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        this.registerReceiver(mReceiver, filter);

        discoveringDevices();

    }

    private void discoveringDevices() {

    }

//    // Create a BroadcastReceiver for ACTION_FOUND
//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            // When discovery finds a device
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Get the BluetoothDevice object from the Intent
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // Add the name and address to an array adapter to show in a ListView
//                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        }
//    };
//    // Register the BroadcastReceiver
//    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
//

    private void queryingPairedDevices() {
        Toast.makeText(this, "queryingPairedDevices", Toast.LENGTH_LONG).show();

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }

        /**
         * All that's needed from the BluetoothDevice object in order to initiate a connection is the MAC address.
         * In this example, it's saved as a part of an ArrayAdapter that's shown to the user.
         * The MAC address can later be extracted in order to initiate the connection.
         * You can learn more about creating a connection in the section about Connecting Devices.
         * */
    }

    private void settingUpBluetooth() {
        Toast.makeText(this,"setting up bluetooth", Toast.LENGTH_LONG).show();
        /**
         * A dialog will appear requesting user permission to enable Bluetooth, as shown in Figure 1.
         * If the user responds "Yes," the system will begin to enable Bluetooth and focus will return to your application
         * once the process completes (or fails).
         *
         * The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0),
         that the system passes back to you in your onActivityResult() implementation as the requestCode parameter.
         If enabling Bluetooth succeeds, your activity receives the RESULT_OK result code in the onActivityResult() callback.
         If Bluetooth was not enabled due to an error (or the user responded "No") then the result code is RESULT_CANCELED.


         Optionally, your application can also listen for the ACTION_STATE_CHANGED broadcast Intent,
         which the system will broadcast whenever the Bluetooth state has changed.
         This broadcast contains the extra fields EXTRA_STATE and EXTRA_PREVIOUS_STATE,
         containing the new and old Bluetooth states, respectively. Possible values for these extra fields are STATE_TURNING_ON,
         STATE_ON, STATE_TURNING_OFF, and STATE_OFF. Listening for this broadcast can be useful to detect changes
         made to the Bluetooth state while your app is running.*/

        //1. Get the Bluetooth Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         *
         If enabling Bluetooth succeeds, your activity receives the RESULT_OK result code in the onActivityResult() callback.
         If Bluetooth was not enabled due to an error (or the user responded "No") then the result code is RESULT_CANCELED.
         */
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    Toast.makeText(this, "BLUETOOTH SETTING RESULT_OK", Toast.LENGTH_LONG).show();
                }else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(this, "BLUETOOTH SETTING RESULT_CANCELED", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnBLEStart:
                Toast.makeText(this, "BLUETOOTH SCAN Start", Toast.LENGTH_LONG).show();
                bluetoothLowEnergy();
                break;
            case R.id.btnBLEStop:
                Toast.makeText(this, "BLUETOOTH SCAN Stop", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // The on-click listener for all devices in the ListViews
    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int position, long id) {
            // Cancel discovery because it's costly and we're about to connect
            //mBluetoothAdapter.cancelDiscovery();
            String msg = "";
            switch (av.getId()){
                case R.id.paired_devices:
                    msg = mPairedDevicesArrayAdapter.getItem(position);
                    break;
                case R.id.new_devices:
                    msg = mNewDevicesArrayAdapter.getItem(position);
                    break;
                case R.id.stored_devices:
                    msg = String.valueOf(mStoredDeviceArrayAdapter.getItem(position));
                    break;
            }

            Toast.makeText(BluetoothActivity.this, msg, Toast.LENGTH_LONG).show();

            // Get the device MAC address, which is the last 17 chars in the View
//            String info = ((TextView) v).getText().toString();
//            String address = info.substring(info.length() - 17);
//
//            // Create the result Intent and include the MAC address
//            Intent intent = new Intent();
//            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
//
//            // Set result and finish this Activity
//            setResult(Activity.RESULT_OK, intent);
//            finish();
        }
    };


//    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            // When discovery finds a device
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // Get the BluetoothDevice object from the Intent
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                // If it's already paired, skip it, because it's been listed already
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//                }
//                // When discovery is finished, change the Activity title
//            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                setProgressBarIndeterminateVisibility(false);
//                setTitle(R.string.select_device);
//                if (mNewDevicesArrayAdapter.getCount() == 0) {
//                    String noDevices = getResources().getText(R.string.none_found).toString();
//                    mNewDevicesArrayAdapter.add(noDevices);
//                }
//            }
//        }
//    };
//
//    private class ScanResultAdaptor extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return null;
//        }
//    }

}
