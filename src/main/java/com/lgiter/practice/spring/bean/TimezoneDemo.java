package com.lgiter.practice.spring.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgiter.practice.spring.anno.EnableTimezoneConvert;
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
@EnableTimezoneConvert
public class TimezoneDemo {

    private String time = "2024-01-01 12:00:00";


    @TimezoneConvert(pattern = "yyyy-MM-dd HH:mm:ss", frontendTimezone = Timezone.UTC3)
    private String time3 = "2024-01-01 12:00:00";


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime china = LocalDateTime.now();

    @TimezoneConvert(frontendTimezone = Timezone.UTC1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime germany = LocalDateTime.now();


    private TimezoneDemo child;

}
