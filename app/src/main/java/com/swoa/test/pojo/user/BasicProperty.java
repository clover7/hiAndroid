package com.swoa.test.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicProperty {
    @Getter @Setter private String birthday;
    @Getter @Setter private String sex;//MALE FEMALE을 문자열로 설정
    @Getter @Setter private String userID;
    @Getter @Setter private String userPW;
    @Getter @Setter private String email;
    @Getter @Setter private String photoUrl;
    @Getter @Setter private UserName userName;
    @Getter @Setter private Address address;
    @Getter @Setter private PhoneNumber phoneNumber;

    @Getter @Setter private String userAuthoritiesType;
    @Getter @Setter private String roles;
    @Getter @Setter private String securityLevel;
    @Getter @Setter private String privacyLevel;
    @Getter @Setter private String lastLogin;
    @Getter @Setter private String ssn;
    @Getter @Setter private String marriage;

}
