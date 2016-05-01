package com.swoa.test.pojo;

import com.swoa.test.pojo.user.AdditionalProperty;
import com.swoa.test.pojo.user.BasicProperty;
import com.swoa.test.pojo.user.StatusProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class UserProperties {
    @Getter @Setter private BasicProperty  basicProperty;
    @Getter @Setter private StatusProperty  statusProperty ;
    @Getter @Setter private AdditionalProperty  additionalProperty ;
}
