package com.clover.seishun.hiandroid.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.util.HashMap;
import java.util.Set;

public class BluetoothLEActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_NEW_ACTIVITY = 0;
    private static final long SCAN_PERIOD = 10000;

    BluetoothAdapter mBluetoothAdapter;

    //데이터를 넣을 adapter
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewBLEDevicesListAdapter;
    private ArrayAdapter<CharSequence> mStoredDeviceArrayAdapter;
    public boolean mScanning;
    public Handler mHandler;
    public int i=0;
    public HashMap<String, Object> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_le);
        mHandler = new Handler();
//        TextView textView = (TextView)findViewById(R.id.txtBLEContext);
//        textView.setText(R.string.ble_context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**onResume, onPostResume 차이는... 뭐지지*/

        TextView textView1 = (TextView)findViewById(R.id.title_ble_stored_devices);
        textView1.setText("Stored Devices");

        TextView textView2 = (TextView)findViewById(R.id.title_ble_new_devices);
        textView2.setText("New Devices");

        TextView textView3 = (TextView)findViewById(R.id.title_ble_paired_devices);
        textView3.setText("Paired Devices");
        mStoredDeviceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.devices, android.R.layout.simple_list_item_1);

        ListView storedDevicesListView = (ListView)findViewById(R.id.ble_stored_devices);
        storedDevicesListView.setAdapter(mStoredDeviceArrayAdapter);
        storedDevicesListView.setOnItemClickListener(mDeviceClickListener);
    }

    private void bluetoothLowEnergy() {
        settingUpBLE();

        findingBLEDevices();
        connectingGATTServer();
        readingBLEAttributes();
        receivingGATTNotifications();
        closingClientApp();
    }

    private void closingClientApp() {

//        /**
//         * Once your app has finished using a BLE device,
//         * it should call close() so the system can release resources appropriately:*/
//        if (mBluetoothGatt == null) {
//            return;
//        }
//        mBluetoothGatt.close();
//        mBluetoothGatt = null;
    }

    private void receivingGATTNotifications() {
    }

    private void readingBLEAttributes() {

    }

    private void connectingGATTServer() {
//        connectingServer();
//        ConnectingClient();


    }

    private void queryingPairedDevices() {
        //Toast.makeText(this, "queryingPairedDevices", Toast.LENGTH_SHORT).show();

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mNewBLEDevicesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
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

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String msg = "";
            switch (parent.getId()){
                case R.id.ble_paired_devices:
                    msg = mPairedDevicesArrayAdapter.getItem(position);
                    break;
                case R.id.ble_new_devices:
                    msg = mNewBLEDevicesListAdapter.getItem(position);
                    break;
                case R.id.ble_stored_devices:
                    msg = String.valueOf(mStoredDeviceArrayAdapter.getItem(position));
                    break;
            }
            Toast.makeText(BluetoothLEActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };

    private void findingBLEDevices() {

        queryingPairedDevices();

        // Find and set up the ListViewActivity for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.ble_paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListViewActivity for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.ble_new_devices);
        newDevicesListView.setAdapter(mNewBLEDevicesListAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

         //discoveringDevices_usingReceiver();
         //discoveringDevices_mLeScanCallback;

        scanLeDevice(true);
        /**
         * To find BLE devices, you use the startLeScan() method.
         * This method takes a BluetoothAdapter.LeScanCallback as a parameter.
         * You must implement this callback, because that is how scan results are returned.
         * Because scanning is battery-intensive, you should observe the following guidelines:
         */
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    i++;
                    String msg = device.getName() + ":: " + device.getAddress();
                    Toast.makeText(BluetoothLEActivity.this, "mLeScanCallback ::" +i + " ->" + msg, Toast.LENGTH_SHORT).show();
                    if(deviceList.containsKey(device.getName()) &
                            deviceList.containsValue(device.getAddress()))
                        return;

                    deviceList.put(device.getName(),device.getAddress());
                    mNewBLEDevicesListAdapter.add(device.getName() + ":: " + device.getAddress());
//                    mLeDeviceListAdapter.addDevice(device);
//                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    private void discoveringDevices_usingReceiver() {

        // Create a BroadcastCustomReceiver for ACTION_FOUND
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            String msg = "";
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                msg = device.getName() + "\n" + device.getAddress();
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewBLEDevicesListAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
                //setTitle(R.string.select_device);
                if (mNewBLEDevicesListAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewBLEDevicesListAdapter.add(noDevices);
                }
            }

            Toast.makeText(BluetoothLEActivity.this, "onReceive ::" + action + " -> "+msg, Toast.LENGTH_SHORT).show();

        }
    };


    private void settingUpBLE() {
        //1. Get the Bluetooth Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //2.Enable Bluetooth
        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        String msg = "";
        switch (requestCode){
            case REQUEST_NEW_ACTIVITY:
                if(resultCode == RESULT_OK){
                    msg = "NEW ACTIVITY : RESULT OK";
                }else if(resultCode == RESULT_CANCELED){
                    msg = "NEW ACTIVITY : RESULT CANCELED";
                }
                break;
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    msg = "ENABLE BT : RESULT OK";
                    bluetoothLowEnergy();
                }else if(resultCode == RESULT_CANCELED){
                    msg = "ENABLE BT : RESULT CANCELED";
                }
                break;
        }
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnBLEStart:
                Toast.makeText(BluetoothLEActivity.this, "BLE SCAN Start", Toast.LENGTH_SHORT).show();
                deviceList = new HashMap<>();
                i++;
                bluetoothLowEnergy();
                break;
            case R.id.btnBLEStop:
                Toast.makeText(BluetoothLEActivity.this, "BLE SCAN Stop", Toast.LENGTH_SHORT).show();
                closingClientApp();
                break;
            case R.id.btnBTSetting:
                //bt 설정화면
                Toast.makeText(this, "BLUETOOTH SETTING", Toast.LENGTH_SHORT).show();
                Intent btSettingIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivityForResult(btSettingIntent, REQUEST_NEW_ACTIVITY);
                break;
        }
    }

}
