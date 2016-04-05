package com.swoa.test;

import com.swoa.test.pojo.AdditionalProperty;
import com.swoa.test.pojo.Address;
import com.swoa.test.pojo.BasicProperty;
import com.swoa.test.pojo.PhoneNumber;
import com.swoa.test.pojo.StatusProperty;
import com.swoa.test.pojo.UserName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class DeviceInfo {
    @Getter @Setter private String email = "";
    @Getter @Setter private String photoUrl = "";

    @Getter @Setter private List<BasicProperty> basicProperty;
    @Getter @Setter private List<UserName> userName;
    @Getter @Setter private List<Address> address;
    @Getter @Setter private List<PhoneNumber> phoneNumber;
    @Getter @Setter private List<StatusProperty> statusProperty ;
    @Getter @Setter private List<AdditionalProperty> additionalProperty ;

}
