package com.lgiter.practice.spring.timezone;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import com.lgiter.practice.spring.enums.Timezone;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: lixiaolong
 * Date: 2024/1/18
 * Desc:
 */
public abstract class FieldTypeProcesser<T> {

    public static final Map<String,FieldTypeProcesser> defalutMap = new HashMap<>();
    public static Map<String,FieldTypeProcesser> extendMap = new HashMap<>();

    abstract LocalDateTime transform(Field field, Object o);

    public void process(Field field, Object o,  TimezoneConvert fieldAnnotation) throws IllegalAccessException {
        Timezone targetTimezone = fieldAnnotation.targetTimezone();
        Timezone localTimezone = fieldAnnotation.localTimezone();
        LocalDateTime transform = transform(field, o);
        ZonedDateTime dateTime = transform.atZone(ZoneId.of(localTimezone.getValue())).withZoneSameInstant(ZoneId.of(targetTimezone.getValue()));
        Date format = Date.from(dateTime.toInstant());
        field.set(o,format);

    }

    public String getType(T t){
        return t.getClass().getName();
    }

}
