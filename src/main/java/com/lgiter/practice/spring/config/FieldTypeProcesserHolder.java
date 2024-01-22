package com.lgiter.practice.spring.config;

import com.lgiter.practice.spring.timezone.FieldTypeProcesser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: lixiaolong
 * Date: 2024/1/22
 * Desc:
 */
@Component
public class FieldTypeProcesserHolder implements ApplicationContextAware {

    /**
     * 用于保存接口实现类名及对应的类
     */
    private Map<String, FieldTypeProcesser> map = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, FieldTypeProcesser> beans = applicationContext.getBeansOfType(FieldTypeProcesser.class);
        beans.values().forEach(bean -> map.put(bean.getType().getTypeName(), bean));
    }


    public FieldTypeProcesser getProcesser(Type type){
        return map.get(type.getTypeName());
    }
}
