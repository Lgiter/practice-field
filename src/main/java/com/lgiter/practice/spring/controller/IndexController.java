package com.lgiter.practice.spring.controller;

import com.lgiter.practice.spring.bean.TimezoneDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return demo;
    }


    @ResponseBody
    @PostMapping("/test/write/timezone")
    public TimezoneDemo testWriteTimeZone(@RequestBody TimezoneDemo timezoneDemo){
        log.info("timezoneDemo:{}",timezoneDemo);
        return timezoneDemo;
    }
}
