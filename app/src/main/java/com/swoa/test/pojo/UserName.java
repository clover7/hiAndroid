package com.swoa.test.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class UserName {
    @Getter @Setter
    private String middleName = "";
    @Getter @Setter private String lastName = "";
    @Getter @Setter private String firstName = "";
}
