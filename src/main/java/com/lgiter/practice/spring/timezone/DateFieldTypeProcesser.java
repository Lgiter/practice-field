package com.lgiter.practice.spring.timezone;

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
public class DateFieldTypeProcesser extends FieldTypeProcesser<Date> {

    @Override
    LocalDateTime transform(Field field, Object o) {
        try {
            Date date = (Date) field.get(o);
            Instant instant = date.toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
