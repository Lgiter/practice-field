package com.lgiter.practice.spring.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Author: lixiaolong
 * Date: 2024/1/18
 * Desc:
 */
@Slf4j
public class TimezoneFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("TimezoneFilter 启动");
        filterChain.doFilter(servletRequest,servletResponse);
        String contentType = servletResponse.getContentType();
        log.info("response :{}",contentType);
    }

    @Override
    public void destroy() {

    }
}
