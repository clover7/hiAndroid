package com.swoa.test.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class BasicProperty {
    @Getter @Setter private String birthday = "";
    @Getter @Setter private String sex = "";//MALE FEMALE을 문자열로 설정
    @Getter @Setter private String userID = "";
    @Getter @Setter private String userPW;

    @Getter @Setter private UserName userName;
    @Getter @Setter private String userAuthoritiesType;
    @Getter @Setter private Address address;
    @Getter @Setter private String roles = null;
    @Getter @Setter private String securityLevel = null;
    @Getter @Setter private String privacyLevel = null;
    @Getter @Setter private String lastLogin = null;
    @Getter @Setter private String ssn = null;
    @Getter @Setter private String email = "";

    @Getter @Setter public PhoneNumber phoneNumber;
    @Getter @Setter private String photoUrl = "";
    @Getter @Setter private String marriage = null;

}
