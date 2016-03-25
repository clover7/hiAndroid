package com.clover.seishun.hiandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clover.seishun.hiandroid.android.AdapterViewActivity;
import com.clover.seishun.hiandroid.android.BroadcastReceiverActivity;
import com.clover.seishun.hiandroid.android.ContentProviderActivity;
import com.clover.seishun.hiandroid.android.IntentActivity;
import com.clover.seishun.hiandroid.android.LayoutActivity;
import com.clover.seishun.hiandroid.android.ListViewActivity;
import com.clover.seishun.hiandroid.android.ServiceActivity;

public class AndroidActivity extends AppCompatActivity {
    final String TAG = AndroidActivity.class.getSimpleName();
    final static int ACT_VIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(savedInstanceState == null){
            Toast.makeText(this, TAG + ":: savedInstanceState is null", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, TAG + ":: savedInstanceState > " + savedInstanceState, Toast.LENGTH_SHORT).show();
        }
//        SharedPreferences pref = getSharedPreferences("SaveState",0);
//        int y =pref.getInt("y", 50);
//
//        View vw = new View(this);
//        vw.setFocusable(true);
//        vw.setFocusable(true);
//        setContentView(vw);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnAndroidFinish = (Button)findViewById(R.id.btnAndroidFinish);
        btnAndroidFinish.setText(R.string.btn_Finish);
        btnAndroidFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnLayout = (Button)findViewById(R.id.btnLayout);
        btnLayout.setText("Layout");

        Button btnActivity = (Button)findViewById(R.id.btnActivity);
        btnActivity.setText("Activity");

        Button btnCP = (Button)findViewById(R.id.btnCP);
        btnCP.setText("CP");

        Button btnBR = (Button)findViewById(R.id.btnBR);
        btnBR.setText("BR");

        Button btnService = (Button)findViewById(R.id.btnService);
        btnService.setText("Service");

        Button btnAdaptor = (Button)findViewById(R.id.btnAdapter);
        btnAdaptor.setText("Adapter");

        Button btnListView = (Button)findViewById(R.id.btnListView);
        btnListView.setText("ListView");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences( "SaveState",0);
        SharedPreferences.Editor edit = pref.edit();
        int y=0;
        edit.putInt("y" , y);
        edit. commit();
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBundle("saveInstance", outState);
    }

    public void mOnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btnLayout:
                intent = new Intent(this, LayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btnActivity:
                intent = new Intent(this, IntentActivity.class);
                /**호출한 대상을 나타내는 식별자.
                 * 리턴 시에 누구에 대한 리턴인가를 구분할 때 사용
                 * 여러 액티비티를 호출할 경우 리턴을 받는 메서드에서 어떤 액티비티에 대한 리턴인지 구분해야 하기 때문이다
                 * 0 이상의 중복되지 않는 정수 (음수를 넘길경우 리턴을 받지 않겠다는 뜻)
                 */
                intent.putExtra("reqText", ">> 명시적 Intent 사용하기");
                startActivityForResult(intent, ACT_VIEW);

                /** 호출한 액티비티가 종료되면 다음 메서드가 호출된다 */
                break;
            case R.id.btnAdapter:
                intent = new Intent(this, AdapterViewActivity.class);
                startActivity(intent);
                break;

            case R.id.btnListView:
                intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                break;

            case R.id.btnCP:
                intent = new Intent(this, ContentProviderActivity.class);
                startActivity(intent);
                break;
            case R.id.btnBR:
                intent = new Intent(this, BroadcastReceiverActivity.class);
                startActivity(intent);
                break;
            case R.id.btnService:
                intent = new Intent(this, ServiceActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * AndroidActivity
     * 자신의 존재를 나타내고 닫는 기능만 수행
     *
     * -Activity 클래스를 상속받으며 onCreate를 재정의하고 super.onCreate까지 호출하여 기본적인 초기화를 수행한다.
     * -버튼 클릭 이벤트에서 finish메소드를 호출하여 해당액티비티를 종료한다.
     * -MainActivity 위에 새로운 SubActivity가 추가로 열린것이다
     * -화면의 close를 누르면 서브액티비티가 종료되고 메인액티비티로 다시 돌아간다.
     */

    /** Activity
     * * 1. 새로 만들 액티비티의 레이아웃을 xml 파일에 정의
     * * 2. 액티비티의 코드를 java파일에 작성
     * * 3. 새로 추가한 액티비티를 AndroidManifest.xml에 등록
     *  <activity android:name=".AndroidActivity"                    //액티비티 이름 (패키지에 포함되어 있으면 .만 적어도 무방함
                    android:label="@string/title_activity_sub"     //타이틀바에 표시될 제목
                     android:theme="@style/AppTheme.NoActionBar" >
        </activity>
     * * 4. startActivity()매서드로 액티비티 호출
     *
     * String.xml 에 제목을 지정하고 리소스를 참조하는 것이 공식적인 형식
     *
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACT_VIEW:
                if(resultCode == RESULT_OK){
                    Toast.makeText(AndroidActivity.this, data.getStringExtra("resText").toString() ,Toast.LENGTH_LONG).show();
                }else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(AndroidActivity.this,  "RESULT_CANCELED" ,Toast.LENGTH_LONG).show();
                }
                break;
        }

        /**
         * setResult로 intent를 전달 받은 액티비티는 (호출원 액티비티) onActivityResult 메서드로 전달 받는다
         * 요청코드 처리결과 인텐트가 인수로 전달되므로 해당 정보를 참조하여 액티비티의 동작 결과를 사용한다.
         * */
    }

}
