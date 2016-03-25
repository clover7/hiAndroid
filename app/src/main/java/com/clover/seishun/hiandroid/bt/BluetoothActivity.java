package com.clover.seishun.hiandroid.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;


    BluetoothAdapter mBluetoothAdapter;

    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private ArrayAdapter<CharSequence> mStoredDeviceArrayAdapter;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Connection devices (pairing하기위해서 우선 uuid선언)
    private static final UUID UUID_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    private static final UUID MY_UUID = UUID.fromString("");
    private static final String NAME = "";
    private static final int MESSAGE_READ =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        TextView textView = (TextView)findViewById(R.id.txtBLEContext);
        textView.setText("BLUETOOTH");

        TextView textView1 = (TextView)findViewById(R.id.title_stored_devices);
        textView1.setText("Stored Devices");

        TextView textView2 = (TextView)findViewById(R.id.title_new_devices);
        textView2.setText("New Devices");

        TextView textView3 = (TextView)findViewById(R.id.title_paired_devices);
        textView3.setText("Paired Devices");


        mStoredDeviceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.devices, android.R.layout.simple_list_item_1);

        ListView storedDevicesListView = (ListView)findViewById(R.id.stored_devices);
        storedDevicesListView.setAdapter(mStoredDeviceArrayAdapter);
        storedDevicesListView.setOnItemClickListener(mDeviceClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();

            // Unregister broadcast listeners
            this.unregisterReceiver(mReceiver);
        }
        finish();
    }

    private void bluetoothLowEnergy() {
        settingUpBluetooth();
        findingBTDevices();
        connectingDevices();
        managingConnection();
        workingProfiles();
    }

    private void workingProfiles() {

    }

    private void managingConnection() {

    }

    private void connectingDevices() {

        /**
         * BluetoothSocket을 생성 : 소켓 생성하기 위해 UUID 필요 (BluetoothProfile에 따라 달라짐, 연동을 하기 위한 목적들을 분류시킨 것)
         자주 쓰이는 BluetoothProfile은 데이터 통신을 위한 SPP, 오디오 스트리밍을 위한 A2DP,리모트 컨트롤을 위한 AVRCP 등이 있다.
         자세한 BluetoothProfile정보는 (https://en.wikipedia.org/wiki/List_of_Bluetooth_profiles)

         이제 연결하려는 BluetoothProfile의 UUID 값을 알아야한다다. (http://dsnight.tistory.com/13)

         이제 BluetoothSocket을 획득해야 합니다. BluetoothSocket은 BluetoothDevice객체에서
         createInsecureRfcommSocketToServiceRecord 메소드를 호출해서 만들 수 있다.*/

        connectingServer();
//        ConnectingClient();
    }

    private void connectingServer() {
        /**
         * When you want to connect two devices, one must act as a server by holding an open BluetoothServerSocket.
         * The purpose of the server socket is to listen for incoming connection requests and when one is accepted,
         * provide a connected BluetoothSocket. When the BluetoothSocket is acquired from the BluetoothServerSocket,
         * the BluetoothServerSocket can (and should) be discarded, unless you want to accept more connections.
         * */

        /**
         * Get a BluetoothServerSocket by calling the listenUsingRfcommWithServiceRecord(String, UUID).
         The string is an identifiable name of your service, which the system will automatically write to a new Service Discovery Protocol
         (SDP) database entry on the device (the name is arbitrary and can simply be your application name).
         The UUID is also included in the SDP entry and will be the basis for the connection agreement with the client device.
         That is, when the client attempts to connect with this device, it will carry a UUID that uniquely identifies the service with
         which it wants to connect. These UUIDs must match in order for the connection to be accepted (in the next step).
         */

        BluetoothServerSocket bluetoothServerSocket;

    }

    private void findingBTDevices() {
        //Toast.makeText(this, "findBLEDevices", Toast.LENGTH_LONG).show();

        /**기존의 연결된 기기 검색
         * BT 연결 가능한 기기 검색 */
        queryingPairedDevices();

        // Find and set up the ListViewActivity for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListViewActivity for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);
//
        discoveringDevices();
    }

    private void discoveringDevices() {

        // Create a BroadcastCustomReceiver for ACTION_FOUND
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Indicate scanning in the title
       // setProgressBarIndeterminateVisibility(true);
//        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        //findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
    }

    private void queryingPairedDevices() {
        //Toast.makeText(this, "queryingPairedDevices", Toast.LENGTH_SHORT).show();

        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

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

    private void settingUpBluetooth() {
        //Toast.makeText(this,"setting up bluetooth", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(this, "BLUETOOTH SETTING RESULT_OK", Toast.LENGTH_SHORT).show();
                    discoveringDevices();
                }else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(this, "BLUETOOTH SETTING RESULT_CANCELED", Toast.LENGTH_SHORT).show();
                }
        }
    }


//    private class AcceptThread extends Thread {
//        private final BluetoothServerSocket mmServerSocket;
//
//        public AcceptThread() {
//            // Use a temporary object that is later assigned to mmServerSocket,
//            // because mmServerSocket is final
//            BluetoothServerSocket tmp = null;
//            try {
//                // MY_UUID is the app's UUID string, also used by the client code
//                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//            } catch (IOException e) { }
//            mmServerSocket = tmp;
//        }
//
//        public void run() {
//            BluetoothSocket socket = null;
//            // Keep listening until exception occurs or a socket is returned
//            while (true) {
//                try {
//                    socket = mmServerSocket.accept();
//                } catch (IOException e) {
//                    break;
//                }
//                // If a connection was accepted
//                if (socket != null) {
//                    // Do work to manage the connection (in a separate thread)
//                    manageConnectedSocket(socket);
//                    try {
//                        mmServerSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//
//        /** Will cancel the listening socket, and cause the thread to finish */
//        public void cancel() {
//            try {
//                mmServerSocket.close();
//            } catch (IOException e) { }
//        }
//    }
//    private class ConnectThread extends Thread {
//        private final BluetoothSocket mmSocket;
//        private final BluetoothDevice mmDevice;
//
//        public ConnectThread(BluetoothDevice device) {
//            // Use a temporary object that is later assigned to mmSocket,
//            // because mmSocket is final
//            BluetoothSocket tmp = null;
//            mmDevice = device;
//
//            // Get a BluetoothSocket to connect with the given BluetoothDevice
//            try {
//                // MY_UUID is the app's UUID string, also used by the server code
//                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//            } catch (IOException e) { }
//            mmSocket = tmp;
//        }
//
//        public void run() {
//            // Cancel discovery because it will slow down the connection
//            mBluetoothAdapter.cancelDiscovery();
//
//            try {
//                // Connect the device through the socket. This will block
//                // until it succeeds or throws an exception
//                mmSocket.connect();
//            } catch (IOException connectException) {
//                // Unable to connect; close the socket and get out
//                try {
//                    mmSocket.close();
//                } catch (IOException closeException) { }
//                return;
//            }
//
//            // Do work to manage the connection (in a separate thread)
//            manageConnectedSocket(mmSocket);
//        }
//
//        /** Will cancel an in-progress connection, and close the socket */
//        public void cancel() {
//            try {
//                mmSocket.close();
//            } catch (IOException e) { }
//        }
//    }

    private void manageConnectedSocket(BluetoothSocket mmSocket) {

        /**
         * manageConnectedSocket() is a fictional method in the application that will initiate the thread for transferring data,
         * which is discussed in the section about Managing a Connection.
         */
    }

//    private class ConnectedThread extends Thread {
//        private final BluetoothSocket mmSocket;
//        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;
//
//        public ConnectedThread(BluetoothSocket socket) {
//            mmSocket = socket;
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//
//            // Get the input and output streams, using temp objects because
//            // member streams are final
//            try {
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) { }
//
//            mmInStream = tmpIn;
//            mmOutStream = tmpOut;
//        }
//
//        public void run() {
//            byte[] buffer = new byte[1024];  // buffer store for the stream
//            int bytes; // bytes returned from read()
//
//            // Keep listening to the InputStream until an exception occurs
//            while (true) {
//                try {
//                    // Read from the InputStream
//                    bytes = mmInStream.read(buffer);
//                    // Send the obtained bytes to the UI activity
//                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
//                            .sendToTarget();
//                } catch (IOException e) {
//                    break;
//                }
//            }
//        }
//
//        /* Call this from the main activity to send data to the remote device */
//        public void write(byte[] bytes) {
//            try {
//                mmOutStream.write(bytes);
//            } catch (IOException e) { }
//        }
//
//        /* Call this from the main activity to shutdown the connection */
//        public void cancel() {
//            try {
//                mmSocket.close();
//            } catch (IOException e) { }
//        }
//    }

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.btnBTStart:
                Toast.makeText(this, "BLUETOOTH SCAN Start", Toast.LENGTH_LONG).show();
                bluetoothLowEnergy();
                break;
            case R.id.btnBTStop:
                Toast.makeText(this, "BLUETOOTH SCAN Stop", Toast.LENGTH_LONG).show();
                mBluetoothAdapter.cancelDiscovery();
                break;
            case R.id.btnBTSetting:
                //bt 설정화면
                Toast.makeText(this, "BLUETOOTH SETTING", Toast.LENGTH_LONG).show();
                Intent btSettingIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(btSettingIntent);
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


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
                //setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };
}
