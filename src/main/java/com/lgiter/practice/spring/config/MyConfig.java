package com.lgiter.practice.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lgiter.practice.spring.filter.TimezoneFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @Author: lixiaolong
 * @Description:
 * @Date: 2021/10/26
 *
 * proxyBeanMethods：是否启用代理bean的方法
 *  Full(proxyBeanMethods = true),全模式，默认
 *  Lite(proxyBeanMethods = false)，轻量级模式，Spring不会检查组件中依赖的其他组件是不是在容器中已经存在了
 *  这个参数用来解决组件依赖问题
 *  比如A、B两个组件，A依赖B组件
 *  如果是全模式，A.getB 拿到的B跟容器中的B是同一个
 *  如果是轻量级模式，A.getB 拿到的B跟容器中的是两个Bean
 */
@Configuration(proxyBeanMethods = false)
public class MyConfig {


    @Bean
    public FilterRegistrationBean getTimezoneFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        TimezoneFilter filter = new TimezoneFilter();
        registration.setFilter(filter);
        registration.setUrlPatterns(Arrays.asList("/*"));
        return registration;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // other serializer and deSerializer config ...

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }


}
