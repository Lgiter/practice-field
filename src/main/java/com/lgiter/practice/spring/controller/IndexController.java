package com.lgiter.practice.spring.controller;

import com.lgiter.practice.spring.bean.TimezoneDemo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: lixiaolong
 * @Description: 首页Controller
 * @Date: 2021/9/27
 */
@Controller
public class IndexController {

    @ResponseBody
    @RequestMapping("/test/timezone")
    public TimezoneDemo testTimeZone(){
        TimezoneDemo demo = new TimezoneDemo();
        return demo;
    }
}
