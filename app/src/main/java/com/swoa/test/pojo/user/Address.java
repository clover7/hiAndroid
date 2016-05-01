package com.swoa.test.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    @Getter @Setter private String zipcode;
    @Getter @Setter private String subAddress;
    @Getter @Setter private String detailAddress;
    @Getter @Setter private String dong;
    @Getter @Setter private String ho;

    @Getter @Setter private String apt;

}
