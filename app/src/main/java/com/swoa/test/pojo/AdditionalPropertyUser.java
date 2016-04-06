package com.swoa.test.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 6..
 */
public class AdditionalPropertyUser extends AdditionalProperty {

    @Getter
    @Setter
    private String lastLoginTime;
    @Getter @Setter private String rfid;
    @Getter @Setter private String userSpecificInfo;
}
