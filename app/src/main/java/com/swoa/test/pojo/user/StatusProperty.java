package com.swoa.test.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusProperty {
    @Getter @Setter
    private String userLocation = "";
    @Getter @Setter
    private String terminalInfo = "";
    @Getter @Setter
    private String status;
}
