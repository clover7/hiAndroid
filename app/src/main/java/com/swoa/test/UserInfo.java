package com.swoa.test;

import com.swoa.test.pojo.AdditionalProperty;
import com.swoa.test.pojo.BasicProperty;
import com.swoa.test.pojo.StatusProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class UserInfo {
//    @Getter @Setter private String

     @Getter @Setter private String createdDate = "";
     @Getter @Setter private String updatedDate = "";
     @Getter @Setter private String serviceIDListProperty = "";
     @Getter @Setter private String id = "";
     @Getter @Setter public BasicProperty basicProperty ;
     @Getter @Setter public StatusProperty statusProperty ;
     @Getter @Setter public AdditionalProperty additionalProperty;
}




