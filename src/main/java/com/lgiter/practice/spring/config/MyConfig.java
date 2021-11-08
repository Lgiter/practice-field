package com.lgiter.practice.spring.config;

import org.springframework.context.annotation.Configuration;

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
}
