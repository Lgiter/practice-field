package com.lgiter.practice.spring.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.lgiter.practice.spring.anno.TimezoneConvert;
import com.lgiter.practice.spring.enums.Timezone;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Slf4j
public class UMRMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @SneakyThrows
    @Override
    protected void writePrefix(JsonGenerator generator, Object o) throws IOException {
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            TimezoneConvert fieldAnnotation = field.getAnnotation(TimezoneConvert.class);
            if (fieldAnnotation != null) {
                field.setAccessible(true);
                processField(o, field, fieldAnnotation);
            }
        }
        log.info("开始处理回文");    }



    private void processField(Object o, Field field, TimezoneConvert fieldAnnotation) throws IllegalAccessException {
        Timezone targetTimezone = fieldAnnotation.frontendTimezone();
        Timezone localTimezone = fieldAnnotation.backendTimezone();
        if (field.getType().getName().equals(String.class.getName())) {
            String parttern = fieldAnnotation.pattern();
            String date = field.get(o).toString();
            DateTimeFormatter df = DateTimeFormatter.ofPattern(parttern);
            LocalDateTime dateTime = LocalDateTime.parse(date, df);
            String format = dateTime.atZone(ZoneId.of(localTimezone.getValue())).withZoneSameInstant(ZoneId.of(targetTimezone.getValue())).toLocalDateTime().format(df);
            field.set(o, format);
        } else if (field.getType().getName().equals(Date.class.getName())) {
            Date date = (Date) field.get(o);
            Instant instant = date.toInstant();
            ZonedDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of(localTimezone.getValue())).atZone(ZoneId.of(localTimezone.getValue())).withZoneSameInstant(ZoneId.of(targetTimezone.getValue()));
            Date format = Date.from(dateTime.toInstant());
            field.set(o,format);
        } else if (field.getType().getName().equals(Long.class.getName())) {


        } else if (field.getType().getName().equals(LocalDateTime.class.getName())) {
            LocalDateTime date = (LocalDateTime) field.get(o);
            LocalDateTime dateTime = date.atZone(ZoneId.of(localTimezone.getValue())).withZoneSameInstant(ZoneId.of(targetTimezone.getValue())).toLocalDateTime();
            field.set(o,dateTime);
        }
    }
}
