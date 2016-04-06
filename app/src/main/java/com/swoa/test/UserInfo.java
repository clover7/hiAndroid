package com.swoa.test;

import com.swoa.test.pojo.AdditionalPropertyUser;
import com.swoa.test.pojo.BasicPropertyUser;
import com.swoa.test.pojo.StatusPropertyUser;

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
     @Getter @Setter public BasicPropertyUser basicPropertyUser ;
     @Getter @Setter public StatusPropertyUser statusPropertyUser ;
     @Getter @Setter public AdditionalPropertyUser additionalPropertyUser;
}




