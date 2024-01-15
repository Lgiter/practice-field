package com.lgiter.practice.spring.bean;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import lombok.Data;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Data
public class TimezoneDemo {

    @TimezoneConvert
    public String time1 = "2024-01-01 12:00:00";

    public String time2 = "2024-01-01 12:00:00";
}
