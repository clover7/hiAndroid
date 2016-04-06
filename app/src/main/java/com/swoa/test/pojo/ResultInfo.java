package com.swoa.test.pojo;

import com.swoa.test.DeviceInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 6..
 */
public class ResultInfo extends DeviceInfo{
    @Getter @Setter int code;
    @Getter @Setter String msg;

}
