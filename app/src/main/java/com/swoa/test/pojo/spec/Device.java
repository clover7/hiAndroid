package com.swoa.test.pojo.spec;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 20..
 */
public class Device {

    @Getter @Setter String deviceId;     //Mac Address
    @Getter @Setter String deviceName;
    @Getter @Setter String deviceType;
    @Getter @Setter String location;
    @Getter @Setter String dscIP;
    @Getter @Setter String objectType;
    @Getter @Setter String objectURI;
    @Getter @Setter String modelName;
    @Getter @Setter String serialNo;    //고유 ID 임의로 설정
    @Getter @Setter List<Node> properties;
    @Getter @Setter List<Node> functions;

}
