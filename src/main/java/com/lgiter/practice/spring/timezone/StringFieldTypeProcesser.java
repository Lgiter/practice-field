package com.lgiter.practice.spring.timezone;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Author: lixiaolong
 * Date: 2024/1/18
 * Desc:
 */
@Component
public class StringFieldTypeProcesser extends FieldTypeProcesser<String> {

    @Override
    LocalDateTime read(Field field, Object o, TimezoneConvert fieldAnnotation) {
        try {
            String parttern = fieldAnnotation.pattern();
            String date = field.get(o).toString();
            DateTimeFormatter df = DateTimeFormatter.ofPattern(parttern);
            LocalDateTime dateTime = LocalDateTime.parse(date, df);
            return dateTime;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    String write(LocalDateTime localDateTime,TimezoneConvert fieldAnnotation) {
        return localDateTime.format(DateTimeFormatter.ofPattern(fieldAnnotation.pattern()));
    }
}
