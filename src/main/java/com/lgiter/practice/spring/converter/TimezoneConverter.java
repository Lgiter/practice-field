package com.lgiter.practice.spring.converter;

import com.alibaba.fastjson.JSON;
import com.lgiter.practice.spring.anno.TimezoneConvert;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Slf4j
public class TimezoneConverter implements HttpMessageConverter<Object> {
    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes(MediaType.ALL_VALUE);
    }


    @Override
    public Object read(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @SneakyThrows
    @Override
    public void write(Object o, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            TimezoneConvert fieldAnnotation = field.getAnnotation(TimezoneConvert.class);
            if (fieldAnnotation != null){
                field.setAccessible(true);
                String s = field.get(o).toString();
                field.set(o,s + "_new");
            }
        }
        byte[] bytes = JSON.toJSONString(o).getBytes();
        httpOutputMessage.getBody().write(bytes);
        log.info("开始处理回文");
    }
}