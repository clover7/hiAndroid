package com.swoa.test.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalProperty{
    @Getter @Setter private String nickname = "";
    @Getter @Setter private String description = "";

    @Getter @Setter private String lastLoginTime;
    @Getter @Setter private String rfid;
    @Getter @Setter private String userSpecificInfo;
}
