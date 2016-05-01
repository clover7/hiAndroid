package com.swoa.test.pojo;

import com.swoa.test.pojo.user.AdditionalPropertyUser;
import com.swoa.test.pojo.user.BasicPropertyUser;
import com.swoa.test.pojo.user.ServiceIDListPropertyUser;
import com.swoa.test.pojo.user.StatusPropertyUser;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class User {
//    @Getter @Setter private String
     @Getter @Setter int code;
     @Getter @Setter String msg;
     @Getter @Setter private Date createdDate;
     @Getter @Setter private Date updatedDate;
     @Getter @Setter private String id = "";
     @Getter @Setter private BasicPropertyUser basicProperty ;
     @Getter @Setter private StatusPropertyUser statusProperty ;
     @Getter @Setter private AdditionalPropertyUser additionalProperty;
     @Getter @Setter private ServiceIDListPropertyUser serviceIDListProperty;

}




