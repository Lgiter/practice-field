package com.lgiter.practice.spring.controller;

import com.lgiter.practice.spring.bean.TimezoneDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * @Author: lixiaolong
 * @Description: 首页Controller
 * @Date: 2021/9/27
 */
@Controller
@Slf4j
public class IndexController {

    @ResponseBody
    @RequestMapping("/test/timezone")
    public TimezoneDemo testTimeZone(){
        TimezoneDemo demo = new TimezoneDemo();
        TimezoneDemo child = new TimezoneDemo();
        child.setChina(LocalDateTime.now().plusDays(-1));
        child.setGermany(LocalDateTime.now().plusDays(-1));

        TimezoneDemo child2 = new TimezoneDemo();
        child2.setChina(LocalDateTime.now().plusDays(-1));
        child2.setGermany(LocalDateTime.now().plusDays(-1));
        child.setChild(child2);
        demo.setChild(child);

        return demo;
    }


    @ResponseBody
    @PostMapping("/test/write/timezone")
    public TimezoneDemo testWriteTimeZone(@RequestBody TimezoneDemo timezoneDemo){
        log.info("timezoneDemo:{}",timezoneDemo);
        TimezoneDemo child = new TimezoneDemo();
        child.setChina(LocalDateTime.now().plusDays(-1));
        child.setGermany(LocalDateTime.now().plusDays(-1));
        child.setTime("2023-01-01 12:00:00");
        timezoneDemo.setChild(child);
        return timezoneDemo;
    }
}
