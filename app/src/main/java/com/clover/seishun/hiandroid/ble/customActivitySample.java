package com.clover.seishun.hiandroid.ble;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

class ManyAdapter extends BaseAdapter{
    Context maincon;
    LayoutInflater Inflater;
    Toast mToast;

    public ManyAdapter(Context context){
        maincon = context;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return " "+position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //각 항목의 뷰생성
        String log = "position : "+ position;
        if(convertView == null){
            convertView = Inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            log += "convertView is null";

        }else{
            log += "convertView is not null";
        }
        Log.d("log manyitem", log);

        if(mToast == null){
            mToast = Toast.makeText(maincon,log,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(log);
        }

        TextView txt = (TextView)convertView.findViewById(android.R.id.text1);
        txt.setText("ManyItem" + position);

        return convertView;
    }
}

public class CustomActivitySample extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ((ListView) findViewById(R.id.list)).setAdapter(new ManyAdapter(this));

        FloatingActionButton floatBtn = (FloatingActionButton)findViewById(R.id.btnFab1);
        floatBtn.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomActivitySample.this, "FloatingActionButton", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

    //    public static final String BTNAME_ACCU_CHEK = "ACCU-CHEK";
//
//    private PairingItemAdapter pairingItemAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_activity_sample);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    class PairingItemAdapter extends BaseAdapter {
//        private Context mCtx;
//        private ArrayList<HashMap> arrayList;
//        public boolean isFirstTitle = false;
//
//        public PairingItemAdapter(Context c) {
//            arrayList = new ArrayList<HashMap>();
//            mCtx = c;
//        }
//
//        public void addItem(HashMap item) {
//            if (!isFirstTitle) {
//                item.put("title_status","title");
//                isFirstTitle = true;
//            } else {
//                item.put("title_status","");
//            }
//
//            arrayList.add(item);
//        }
//        public void clearTitleFlag() {
//            isFirstTitle = false;
//        }
//
//        @Override
//        public int getCount() {
//            return arrayList.size();
//        }
//
//        @Override
//        public HashMap<String, Object> getItem(int arg0) {
//            return arrayList.get(arg0);
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = getLayoutInflater();
//            View row = inflater.inflate(R.layout.bluetooth_pairing, parent, false);
//
//            LinearLayout bodyLayout = (LinearLayout)row.findViewById(R.id.paired_body_layout);
//            LinearLayout titleLayout = (LinearLayout)row.findViewById(R.id.paired_title_layout);
//            TextView tv_title = (TextView)row.findViewById(R.id.paired_title);
//            TextView tv_sensor = (TextView)row.findViewById(R.id.paired_device_sensors);
//            TextView tv_product = (TextView)row.findViewById(R.id.paired_device_product);
//            TextView tv_model = (TextView)row.findViewById(R.id.paired_device_model);
//            TextView tv_status = (TextView)row.findViewById(R.id.paired_device_status);
//            TextView tv_serial = (TextView)row.findViewById(R.id.paired_device_serial);
//
//            HashMap<String, Object> map = arrayList.get(position);
//
//            tv_title.setText(map.get("title").toString());
//            tv_sensor.setText(map.get("sensor").toString());
//            tv_product.setText(map.get("product").toString());
//            tv_model.setText(map.get("model").toString());
//            tv_status.setText(map.get("status").toString());
//            tv_serial.setText(map.get("serial").toString());
//
//            if (map.get("title_status").equals("title")) {
//                titleLayout.setVisibility(View.VISIBLE);
//            } else {
//                titleLayout.setVisibility(View.GONE);
//            }
//            if (map.get("status").toString().equals("paired")) {
//                if (map.get("serial").toString().toUpperCase().contains(BTNAME_ACCU_CHEK))
//                    bodyLayout.setClickable(false);
//                else
//                    bodyLayout.setClickable(true);
//            } else {
//                bodyLayout.setClickable(false);     // click
//            }
//            titleLayout.setClickable(true);
////
////            FloatingActionButton fab = (FloatingActionButton)convertView.findViewById(R.id.fab);
////            fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(BluetoothPairingActivity.this, "FloatingActionButton", Toast.LENGTH_SHORT).show();
////                }
////            });
//
//            return row;
//        }
//    }
//}


