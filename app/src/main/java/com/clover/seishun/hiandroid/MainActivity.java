package com.clover.seishun.hiandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clover.seishun.hiandroid.ble.DeviceScanActivity;


public class MainActivity extends Activity {
   final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btn event
        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //버튼의 클릭 이벤트 리스너에서 에디트에 입력된 텍스트를 읽어 토스메세지로 뿌린다.
                EditText edit = (EditText) findViewById(R.id.edit);
                String str = edit.getText().toString();
                Log.d("str : ", str);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        /*ANDROID EXAMPLE*/
        Button btnAndroid = (Button)findViewById(R.id.btnAndroid);
        btnAndroid.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, TAG, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AndroidActivity.class);
                startActivity(intent);
            }
        });


        /*BLUETOOTH EXAMPLE*/
        Button btnBT = (Button)findViewById(R.id.btnBT);
        btnBT.setText(R.string.btn_bt_scan);
        btnBT.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Change Activity >> BluetoothActivity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });

        Button btnCoffee = (Button) findViewById(R.id.btnCoffee);
        btnCoffee.setText(R.string.btn_Coffee);
        btnCoffee.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Change Activity >> CoffeeActivity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CoffeeActivity.class);
                startActivity(intent);
            }
        });
        Button btnBLE = (Button)findViewById(R.id.btnBLE);
        btnBLE.setText(R.string.btn_ble_scan);
        btnBLE.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Change Activity >> DeviceScanActivity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
                startActivity(intent);
            }
        });



        //android auto write
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
