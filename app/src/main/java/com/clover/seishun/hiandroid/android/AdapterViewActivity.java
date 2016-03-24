package com.clover.seishun.hiandroid.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.clover.seishun.hiandroid.R;

public class AdapterViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view);
    }

    /**
     * 1. 리스트뷰
     * 여러개의 항목을 수직으로 표시하는 위젯
     * 수직스크롤 지원
     *
     * 클래스 계층도로 이 위젯들의 목록을 정리해보자.
     *
     *                              <adapter view>
     * - view
     * - view group
     * - adapter view - abslistview - listview - expandlabelListview
     *                              - gridview
     *                - absSpinner  - spinner
     *                              - gallery
     *
     * adapter view : listview ,gridview,spinner,gallery
     *
     * 각 위젯(adapter view)들은 집합을 표시한다는 면에서 기능적으로 동일하지만 항목을 표시하는 방법이 다르다.
     * 이를 adapter view라고 부르는 이유?
     * - 표시할 항목 데이터를 직접 관리하지 않고 "어댑터 객체"로 부터 공급받기 때문이다
     * 항목의 개수는 무한대 실행중 목록이 바뀔 수 있으며 데이터의 원본도 다양하다
     *
     * - 어댑터는 원본으로부터 얻은 데이터를 관리하며 어댑터뷰는 어댑터가 전달한 데이터를 화면에 표히사는 시긍로 분업화 한다.
     *
     * 위젯과 연결될 수 있는 어댑터의 계층
     *
     *                            Adapter
     *               ________________|________________
     *              |                                 |
     *          ListAdapter                     SpinnerAdapter
     *               |________________________________|
     *                               |
     *       ____________________ BaseAdapter ________________
     *      |                         |                       |
     * ArrayAdapter<T>           CursorAdapter          SimpleAdapter
     *
     * Adapter 인터페이스는 어댑터뷰와 데이터간의 연결을 제공하고 항목의 집합을 관리하는 기본적인 메서드를 선언한다.
     * -ListAdapter (인터페이스) : 리스트뷰와 연결에 필요한 메서드를 정의
     * -SpinnerAdapter (인터페이스) : 스피너와 연결에 필요한 메서드를 정의
     *
     *
     *

     * 1.1 어댑터 뷰
     *
     * */
}
