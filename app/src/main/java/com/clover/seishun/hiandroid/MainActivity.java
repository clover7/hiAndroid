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

import com.clover.seishun.hiandroid.ble.BluetoothLEActivity;
import com.clover.seishun.hiandroid.ble.DeviceScanActivity;
import com.clover.seishun.hiandroid.bt.BluetoothActivity;
import com.swoa.test.SwoaMainActivity;


public class MainActivity extends Activity {
   final String TAG = MainActivity.class.getSimpleName();

//    @ViewById(R.id.btnSwoa)
//    Button btnSwoa;
//
//
//    @Click(R.id.btnSwoa)
//    private void btnSwoaClick(){
//        Intent intent = new Intent(this, SwoaMainActivity.class);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate");
        Toast.makeText(this,TAG +"::"+ "onCreate",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnSwoa.setText("SWOA");

        //btn event
        Button btnStart = (Button) findViewById(R.id.btnStart);
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

        Button btnBT = (Button) findViewById(R.id.btnBT);
        btnBT.setText(R.string.btn_bt_scan);

        Button btnCoffee = (Button) findViewById(R.id.btnCoffee);
        btnCoffee.setText(R.string.btn_Coffee);

        Button btnBLE = (Button) findViewById(R.id.btnBLE);
        btnBLE.setText(R.string.btn_ble_scan);

        Button btnSwoa = (Button) findViewById(R.id.btnSwoa);
        btnSwoa.setText("SWOA");
    }

    public void mOnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btnBT:
                intent = new Intent(this, BluetoothActivity.class);
                Toast.makeText(MainActivity.this, "BluetoothActivity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btnAndroid:
                intent = new Intent(this, AndroidActivity.class);
                Toast.makeText(MainActivity.this,"AndroidActivity" , Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btnBLEExample:
                intent = new Intent(this, DeviceScanActivity.class);
                Toast.makeText(MainActivity.this, "DeviceScanActivity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btnCoffee:
                intent = new Intent(this, CoffeeActivity.class);
                Toast.makeText(MainActivity.this, "CoffeeActivity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btnBLE:
                intent = new Intent(this, BluetoothLEActivity.class);
                Toast.makeText(MainActivity.this, "BluetoothLEActivity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

            case R.id.btnSwoa:
                intent = new Intent(this, SwoaMainActivity.class);
                Toast.makeText(MainActivity.this, "SwoaMainActivity", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;

        }

    }

        //android auto write
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        Toast.makeText(this,TAG +"::"+ "onRestart",Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,TAG +"::"+ "onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        Toast.makeText(this,TAG +"::"+ "onStart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        Toast.makeText(this,TAG +"::"+ "onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        Toast.makeText(this,TAG +"::"+ "onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        Toast.makeText(this,TAG +"::"+ "onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        Toast.makeText(this,TAG +"::"+ "onDestroy",Toast.LENGTH_SHORT).show();
    }





    /**
     *
     * 생명주기(LifeCycle)
     *
     * 1. 액티비티의 일생
     *시스템은 태스크의 실행 중인 액티비티를 "스택"으로 관리한다
     *액티비티가 새로 생성되면 스택의 제일 위에 놓여 활성화된다
     *이 상태에서 새로운 액티비티가 또 생성되면 기존 액티비티는 잠시 가려지고 새액티비티가 스택의 제일 위에 배치된다.
     * 스택 제일 위에 액티비티가 종료되면 바로 아래쪽에 있는 액티비티가 자연스럽게 활성화 될 것이다.
     *
     *2. 액티비티의 상태
     * *실행(active, running) : 사용자가 직접 사용하는 상태, 스택의 제일 위에 있으며 화면 상에서도 역시 제일 위에 있다 입력 포커스를 가지며 사용자의 입력을 직접 처리
     * *일시정지(pause) : 포커스는 잃었으나, 사용자에게 보이는 상태. 위쪽에 다른 액티비티가 있지만 화면 전체를 다 가리지 않았거나 반투명한 경우. 살아있는 상태와 같지만 시스템에 의해 강제 종료될 수 있다.
     * *정지(stopped) : 다른 액티비티에 의해 완전히 가려진 상태, 사용자 눈에 보이지 않음. 그러나 모든 정보를 유지하고 있으므로 언제든 다시 활성화할 수 있다.
     * 시스템은 메모리가 부족하면 정지 상태에 액티비티를 언제든 강제 종료 할 수 있다.
     *
     *엑티비티의 상태가 바뀔때 마다 다음과 같은 메서드가 호출 된다.
     *자신의 상태가 바뀔 때 특별한 동작을 하고 싶다면 이 메서드를 재정의하여 원하는 작업을 해야한다.
     * 액티비티의 전체 생명주기와 상태 변화 시에 호출되는 메서드는 다음과 같다.
     *
     *
     @Override
     public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
     //액티비티를 초기화한다 중지했다가 재시작하는 경우라면 이전 상태 정보인 Bundle이 전달되며 이 정보대로 재초기화 한다.
     //액티비티 생성시에 호출되기 때문에, 반드시 구현해야하며 마법사가 미리 메서드를 재정의해놓는다.

     //우선 super.onCrete를 호출하여 액티비티의 기본 초기화를 먼저 수행한다.
     super.onCreate(savedInstanceState, persistentState);

     //setContentView 메서드를 호출하여 액티비티 안을 뷰로 채운다.
     setContentView(R.layout.activity_life_cycle);
     }

     //나머지 메서드는 필요할 때 구현하면 된다. onStart, onStop 같은 경우는 실용성이 없으며 onDestory도 구현하지 않는 경우가 많다.
     //onPause 메서드는 대부분의 경우 구현해야한다.

     @Override
     protected void onRestart() {
     //재시작될 때 호출 된다.
     super.onRestart();
     }

     @Override
     protected void onStart() {
     //액티비티가 사용자에게 보이기 직전에 호출된다.
     super.onStart();
     }

     @Override
     protected void onResume() {
     //사용자와 상호작용을 하기 직전에 호출된다. 이 단계에서 스택의 제일 위로 올라온다.
     super.onResume();
     }

     @Override
     protected void onPause() {
     //다른 액티비티가 실행될 때 호출된다. 이 단계에서 미정한 데이터가 있으면 저장하고 애니메이션은 중지해야한다.
     //이 메서드가 리턴되어야 새 액티비티가 활성화되므로 시간을 너무 오래 끌어서는 안된다.

     //해당 메서드가 호출된 이후 킬러블(killable)상태라고 하며 시스템이 언제든지 액티비티를 강제 종료할 수 있다.
     //강제종료당한 프로그램은 onStop, onDestory가 호출되지 않기 때문에 저장해야할 정보는 반드시 여기에서 저장해야 한다.
     super.onPause();
     }

     @Override
     protected void onStop() {
     //액티비티가 사용자에게 보이지 않게 될 때 호출된다.
     super.onStop();
     }

     @Override
     protected void onDestroy() {
     //액티비티가 파괴될 때 호출된다. 시스템에 의해 강제로 종료되는 것인지 아니면 finish 메서드 호출에 의해 스스로 종료하는 것인지는 isFinishing 메서드로 조사할 수 있다.
     super.onDestroy();
     }

      *강제종료 되기 전으로 복구하는 방법
      * 시스템은 액티비티를 중지시키기 전에 onSaveInstanceState 메서드를 호출하여 임시적인 데이터를 저장할 기회도 제공한다.
      * 이 메서드의 인수로 전달되는 Bundle객체에 필요한 데이터를 저장해 놓는다.
      * 이렇게 작성된 Bundle은 재시작시, onCreate로 전달되며 onRestoreInstanceState로도 전달된다.
      * 이 때, 저장된 정보를 읽어 이전 상태로 복구하면 마치 강제 종료되지 않은 것처럼 복구된다.


      * */
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
