package com.lgiter.practice.spring.config;

import com.lgiter.practice.spring.converter.TimezoneConverter;
import com.lgiter.practice.spring.converter.UMRMappingJackson2HttpMessageConverter;
import com.lgiter.practice.spring.interceptor.TimezoneInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Configuration
public class SpringMvcConfigurer extends WebMvcConfigurationSupport {


    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0,new TimezoneConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimezoneInterceptor());
    }
}
