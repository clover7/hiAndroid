package com.clover.seishun.hiandroid.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothLEActivity extends AppCompatActivity {

    private final static String TAG = BluetoothLEActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_NEW_ACTIVITY = 0;
    private static final long SCAN_PERIOD = 10000;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothGatt mBluetoothGatt;
    String mDeviceAdress;

    //데이터를 넣을 adapter
    private ArrayAdapter<HashMap<String, Object>> mPairedDevicesArrayAdapter;
    private ArrayAdapter<HashMap<String, Object>> mNewBLEDevicesListAdapter;
    private ArrayAdapter<CharSequence> mStoredDeviceArrayAdapter;
    public boolean mScanning;
    public Handler mHandler;
    public int i=0;
    public HashMap<String, Object> deviceList;
    HashMap<String, Object> newDeviceList;
    HashMap<String, Object> pairedDeviceList;

    private String mBluetoothDeviceAddress;

    protected static final int STATE_DISCONNECTED = 0;
    protected static final int STATE_CONNECTING = 1;
    protected static final int STATE_CONNECTED = 2;
    protected static final int STATE_GATT_SUCESS = 3;
    private int mConnectionState;

    BluetoothGattCharacteristic characteristic;


    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
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
        Log.d(TAG, String.valueOf(mConnectionState));

        mPairedDevicesArrayAdapter = new ArrayAdapter<HashMap<String, Object>>(this, android.R.layout.simple_list_item_1);
        mNewBLEDevicesListAdapter = new ArrayAdapter<HashMap<String, Object>>(this, android.R.layout.simple_list_item_1);

        if(mConnectionState == STATE_CONNECTING){
           Log.d(TAG, "STATE_CONNECTING");
           Toast.makeText(BluetoothLEActivity.this ,"STATE_CONNECTING", Toast.LENGTH_SHORT).show();
        }else if(mConnectionState == STATE_CONNECTED){
           Log.d(TAG, "STATE_CONNECTED");
           Toast.makeText(BluetoothLEActivity.this ,"STATE_CONNECTED", Toast.LENGTH_SHORT).show();
       }else if(mConnectionState == STATE_DISCONNECTED) {
           settingUpBLE();
           findingBLEDevices();
           Log.d(TAG, "STATE_DISCONNECTED");
           Toast.makeText(BluetoothLEActivity.this ,"STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
        }
    }

    private void closingClientApp() {
//        /**
//         * Once your app has finished using a BLE device,
//         * it should call close() so the system can release resources appropriately:*/
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    private void receivingGATTNotifications(BluetoothGattCharacteristic characteristic,
                                            boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;
        return mBluetoothGatt.getServices();
    }

    private void readingBLEAttributes(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    private boolean connectingGATTServer(String address) {

        if (mBluetoothAdapter == null || address == null) { 
            Toast.makeText(this,"BluetoothAdapter not initialized or unspecified address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Toast.makeText(this,"Trying to use an existing mBluetoothGatt for connection.", Toast.LENGTH_SHORT).show();
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                bluetoothLowEnergy();
                return true;
            } else  {
                return false;
            }
        }

//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                mBluetoothGatt = device.connectGatt(BluetoothLeService.this, false, mGattCallback);
//                return null;
//            }
//        }.execute();
////        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
//        Toast.makeText(this,"Trying to create a new connection.", Toast.LENGTH_SHORT).show();
//        mBluetoothDeviceAddress = address;
//        mConnectionState = STATE_CONNECTING;
//        return true;

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Toast.makeText(this,"Device not found. Unable to connect.", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        mBluetoothDeviceAddress = address;
//        mConnectionState = STATE_CONNECTING;
        return true;
    }

    private void queryingPairedDevices() {
        HashMap<String, Object> pairedDevicesList = new HashMap<String, Object>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesList.put("deviceName", device.getName());
                pairedDevicesList.put("deviceAddress", device.getAddress());
                mPairedDevicesArrayAdapter.add(pairedDevicesList);
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            //pairedDevicesList.put("deviceName", noDevices);
            pairedDevicesList.put("deviceName", noDevices);
            mPairedDevicesArrayAdapter.add(pairedDevicesList);
        }

        /**
         * All that's needed from the BluetoothDevice object in order to initiate a connection is the MAC address.
         * In this example, it's saved as a part of an ArrayAdapter that's shown to the user.
         * The MAC address can later be extracted in order to initiate the connection.
         * You can learn more about creating a connection in the section about Connecting Devices.
         * */
    }
    
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnectionState = STATE_CONNECTED;
                scanLeDevice(false);

//                // Attempts to discover services after successful connection.
                Log.d(TAG,"Attempting to start service discovery:" + mBluetoothGatt.discoverServices());
                Log.d(TAG, "Connected to device");

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnectionState = STATE_DISCONNECTED;
//                sendCallbackMessageToJSON(JSONUtil.KEY_STATE, JSONUtil.VAL_STATE_GATT_DISCONNECTED);
                Log.d(TAG,"Device connection was lost");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                Log.w(TAG, "onServicesDiscovered : ACTION_GATT_SERVICES_DISCOVERED " + status);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }

//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                mConnectionState = STATE_GATT_SUCESS;
//                sendCallbackMessageToJSON(JSONUtil.KEY_STATE, JSONUtil.VAL_STATE_GATT_SERVICES_DISCOVERED_SUCCESS);
//
//                if(findStandardService(getSupportedGattServices())) {
//                    readBleCharacteristic();
//                } else{
//                    sendCallbackMessageToJSON(JSONUtil.KEY_STATE, JSONUtil.VAL_STATE_GATT_SERVICE_NOT_FOUND);
//                    showToastMessage("VAL_STATE_GATT_SERVICE_NOT_FOUND");
//                }
//            } else {
//                logger.debug("onServicesDiscovered received: " + status);
//                sendCallbackMessageToJSON(JSONUtil.KEY_STATE, JSONUtil.VAL_STATE_GATT_SERVICES_DISCOVERED_FAIL);
//                showToastMessage("Device connection was lost");
//            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                Log.d(TAG, "onCharacteristicRead");
            }

//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                logger.debug("onCharacteristicRead received: BluetoothGatt.GATT_SUCCESS :" + characteristic.getUuid().toString());
//                final byte[] data = characteristic.getValue();
//                if (data != null && data.length > 0) {
//                    printData(data);
//                }
//                int uid = readBleCharacteristic();
//                logger.debug("==============" + uid + "+++++++++++++++++++");
//                if( uid< 0){
//                    logger.debug("characteristic uuid: " +  characteristic.getUuid());
//                    logger.debug("serviceStatus" + serviceStatus);
//                    enableRXNotification();
//                }
//            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            Log.d(TAG, "onCharacteristicChanged");
            readingBLEAttributes(characteristic);
            receivingGATTNotifications(characteristic, true);

//            logger.debug("onCharacteristicChanged received :" + characteristic.getUuid().toString());
//            final byte[] data = characteristic.getValue();
//            if (!(data != null && data.length > 0)) {
//                logger.debug("onCharacteristicChanged received data ERROR!!!");
//                return;
//            }
//            //showToastMessage("CALL STATUS : " + serviceStatus);
//            printData(data);
//            byte callBackValue = data[0];
//
//            if(!(characteristic.getUuid().equals(RX_CHAR_UUID)))
//                return;

        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    private void broadcastUpdate(String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            }
        }
        sendBroadcast(intent);
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String msg = "";
            String deviceAddress = "";
            switch (parent.getId()){
                case R.id.ble_paired_devices:
                    pairedDeviceList = mPairedDevicesArrayAdapter.getItem(position);
                    deviceAddress = pairedDeviceList.get("deviceAddress").toString();
                    break;
                case R.id.ble_new_devices:
                    newDeviceList = mNewBLEDevicesListAdapter.getItem(position);
                    deviceAddress = newDeviceList.get("deviceAddress").toString();
                    boolean result = false;
                    if(mConnectionState == STATE_CONNECTED){
                        result = connectingGATTServer(deviceAddress);
                    }else{
                        bluetoothLowEnergy();
                    }
                    break;
                case R.id.ble_stored_devices:
                    msg = String.valueOf(mStoredDeviceArrayAdapter.getItem(position));
                    break;
            }
            msg = deviceAddress;
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
//                    Toast.makeText(BluetoothLEActivity.this, "mLeScanCallback ::" +i + " ->" + msg, Toast.LENGTH_SHORT).show();
//
                    if(deviceList.containsValue(device.getName()))
                        if(deviceList.containsValue(device.getAddress()))
                            return;

                    if(mConnectionState == STATE_CONNECTED){

                        deviceList.put("deviceName", device.getName());
                        deviceList.put("deviceAddress", device.getAddress());

                        mPairedDevicesArrayAdapter.add(deviceList);
                     //   mNewBLEDevicesListAdapter.remove(deviceList);
                    }
                        deviceList.put("deviceName", device.getName());
                        deviceList.put("deviceAddress", device.getAddress());

                        mNewBLEDevicesListAdapter.add(deviceList);

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
            HashMap<String, Object> deviceList = null;
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                msg = device.getName() + "\n" + device.getAddress();
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    deviceList.put(device.getName(), device.getAddress());
                    mNewBLEDevicesListAdapter.add(deviceList);
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
                //setTitle(R.string.select_device);
                if (mNewBLEDevicesListAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    deviceList.put(noDevices, "");
                    mNewBLEDevicesListAdapter.add(deviceList);
                }
            }
            else if(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState() == BluetoothDevice.BOND_BONDED)
                    deviceList.put(device.getName(), device.getAddress());
                    mPairedDevicesArrayAdapter.add(deviceList);
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
                i=0;
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
