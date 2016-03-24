package com.clover.seishun.hiandroid.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.clover.seishun.hiandroid.R;

public class ListViewActivity extends AppCompatActivity {


    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //2. 데이터 원본생성
        //ArrayList 객체를 생성
        //데이터 추가
//        ArrayList<String> arGeneral = new ArrayList<>();
//        arGeneral.add("1");
//        arGeneral.add("2");
//        arGeneral.add("3");
//        arGeneral.add("4");

        //3. 어댑터 생성, 초기화
        //데이터원본과 리스트뷰를 연결할 어댑터 생성
        //문자열/배열로 되어 있으므로 ArrayAdapter 사용
//        ArrayAdapter<String> adapter;
//        adapter = new ArrayAdapter<String>(this,        //context(액티비티)
//                android.R.layout.simple_list_item_1,    //항목을 표시할 레이아웃의 리소스 id (텍스트뷰 이미지뷰 체크박스 라디오버튼 등)
//                arGeneral);                             //어댑터로 공급될 데이터의 원본

        //xml로 작성
        adapter = ArrayAdapter.createFromResource(this, R.array.devices, android.R.layout.simple_list_item_1);

        //리소스 배열에 정의해둔 item들이 리스트뷰에 표시된다. 차후 리소스 교체하여 다른언어로도 가능 //리스트는 스크롤 관련 처리를 자체적으로 지원

        /**시스템이 목록 표시용으로 미리 정해놓은 레이아웃 (이외에는 커스텀뷰로 사용)
         *
         * simple_list_item_1 : 하나의 텍스트뷰
         * simple_list_item_1 : 두개의 텍스트뷰
         * simple_list_item_checked : 오른쪽 체크 표시
         * simple_list_item_single_choice : 오른쪽 라디오
         * simple_list_item_multiple_choice : 오른쪽 체크 버튼
         * */

        /**어댑터로 공급될 데이터의 원본 형식
         * 1. 컬랙션 객체 : ArrayList, LinkedList, Stack
         * 2. 단순배열
         *  String[] arGeneral = ["",""];
         *
         * 단, 배열은 초기화할 때 크기가 결정 됨 -> 더이상 데이터를 추가 삭제 못함
         * 정적 데이터인 경우에는 xml리소스를 사용하는 것이 더 유리함.
         * */


        //어댑터 연결
        //1. 레이아웃 안에 리시트 위젯 하나 배치
        //코드에서 어댑터와 연결하기 위해 리스트뷰를 참조할 것으로 id = list로 지명
        //레이아웃은 전체로 체운다
        ListView listView = (ListView)findViewById(R.id.list);

        //4. 리스트뷰에 아래메서드 호출하여 연결
        //어댑터는 이때 리스트뷰에 출력할 원본을 가지며 이원본을 출력할 뷰를 생성하여 리스트뷰에게 제공한다
        //리스트뷰는 어댑터가 제공한 뷰를 사용자에게 보여주며 터치입력을 받아 항목선택 및 스크롤 처리를 한다.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mOnItemClick);

        //항목편집 적용해보기 (p598)
    }

    AdapterView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String msg = String.valueOf(adapter.getItem(position));
            Toast.makeText(ListViewActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };

}
