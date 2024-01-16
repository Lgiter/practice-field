package com.lgiter.practice.spring.bean;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import com.lgiter.practice.spring.enums.Timezone;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Data
public class TimezoneDemo {

    public String time = "2024-01-01 12:00:00";


    @TimezoneConvert(pattern = "yyyy-MM-dd HH:mm:ss", targetTimezone = Timezone.UTC1)
    public String time1 = "2024-01-01 12:00:00";

    @TimezoneConvert(pattern = "yyyy-MM-dd HH:mm:ss", targetTimezone = Timezone.UTC2)
    public String time2 = "2024-01-01 12:00:00";

    @TimezoneConvert(pattern = "yyyy-MM-dd HH:mm:ss", targetTimezone = Timezone.UTC3)
    public String time3 = "2024-01-01 12:00:00";


    public LocalDateTime china = LocalDateTime.now();

    @TimezoneConvert(targetTimezone = Timezone.UTC1)
    public LocalDateTime germany = LocalDateTime.now();




}
