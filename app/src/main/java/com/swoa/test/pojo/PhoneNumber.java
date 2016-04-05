package com.swoa.test.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class PhoneNumber {
    @Getter
    @Setter
    private String home = "";
    @Getter @Setter private String company = "";
    @Getter @Setter private String mobile = "";
}
