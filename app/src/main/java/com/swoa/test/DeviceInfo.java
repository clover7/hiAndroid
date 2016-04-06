package com.swoa.test;

import com.swoa.test.pojo.AdditionalProperty;
import com.swoa.test.pojo.BasicProperty;
import com.swoa.test.pojo.StatusProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by heaun.b on 2016. 4. 5..
 */
public class DeviceInfo{
    @Getter @Setter private BasicProperty  basicProperty;
    @Getter @Setter private StatusProperty  statusProperty ;
    @Getter @Setter private AdditionalProperty  additionalProperty ;



}
