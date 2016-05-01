package com.swoa.test.pojo.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 6..
 */
public class BasicPropertyUser extends BasicProperty {
    @Getter @Setter private String userAuthoritiesType;
    @Getter @Setter private String roles = null;
    @Getter @Setter private String securityLevel = null;
    @Getter @Setter private String privacyLevel = null;
    @Getter @Setter private String lastLogin = null;
    @Getter @Setter private String ssn = null;
    @Getter @Setter private String marriage = null;

    @Getter @Setter private AddressUser address;
}
