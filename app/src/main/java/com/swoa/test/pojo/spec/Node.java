package com.swoa.test.pojo.spec;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 20..
 */
public class Node {

    @Getter @Setter String nodeId;      //앱 안에서의 유니크한값
    @Getter @Setter String nodeType;
    @Getter @Setter String nodeName;    //측정장비 "혈압계"
    @Getter @Setter String name;        //측정항목 : sys
    @Getter @Setter String value;       //측정항목
    @Getter @Setter String unit;        //단위

    /**
     *
     * "properties": [
     //node란 게이트웨이(앱)에 연결된 실제 디바이스. nodeType에 실제 디바이스 타입 설정. 체중계 등을 영문 대문자로 작성
     //nodeId는 게이트웨이(앱)내에서 유일한 값을 사용
     //name에 속성명 설정. 예를 들면 체중 -> weight, value에 값 설정 -> 70, uinit에 단위 설정 -> kg
     //게이트웨이(앱)에 연결된 실제 장비에서 보내줄 속성들을 다중(배열)으로 사용가능
     {“nodeId”:””, “nodeType”:””, “nodeName”:””, "name": "", "value":"“, “unit":""},
     {“nodeId”:””, “nodeType”:””, “nodeName”:””, "name": "", "value":"“, “unit":""}
     ],
     */
}
