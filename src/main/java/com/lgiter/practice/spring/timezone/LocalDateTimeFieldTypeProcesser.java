package com.lgiter.practice.spring.timezone;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Author: lixiaolong
 * Date: 2024/1/18
 * Desc:
 */
@Component
public class LocalDateTimeFieldTypeProcesser extends FieldTypeProcesser<LocalDateTime> {

    @Override
    LocalDateTime read(Field field, Object o, TimezoneConvert fieldAnnotation) {
        try {
            LocalDateTime date = (LocalDateTime) field.get(o);
            return date;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    LocalDateTime write(LocalDateTime localDateTime, TimezoneConvert fieldAnnotation) {
        return localDateTime;
    }

    public static void main(String[] args) {
        FieldTypeProcesser processer = new LocalDateTimeFieldTypeProcesser();
        System.out.println(processer.getType().getTypeName());
    }
}
