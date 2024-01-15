package com.lgiter.practice.spring.config;

import com.lgiter.practice.spring.converter.TimezoneConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Configuration
public class SpringMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TimezoneConverter());

    }


}
