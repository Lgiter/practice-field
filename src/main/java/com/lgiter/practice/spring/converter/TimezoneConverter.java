package com.lgiter.practice.spring.converter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lgiter.practice.spring.anno.EnableTimezoneConvert;
import com.lgiter.practice.spring.anno.TimezoneConvert;
import com.lgiter.practice.spring.bean.TimezoneDemo;
import com.lgiter.practice.spring.config.FieldTypeProcesserHolder;
import com.lgiter.practice.spring.enums.Timezone;
import com.lgiter.practice.spring.timezone.FieldTypeProcesser;
import com.lgiter.practice.spring.util.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author: lixiaolong
 * Date: 2024/1/15
 * Desc:
 */
@Slf4j
@Component
public class TimezoneConverter implements HttpMessageConverter<Object> {


    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return true;
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE);
    }


    protected JavaType getJavaType(Type type, @Nullable Class<?> contextClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        return typeFactory.constructType(GenericTypeResolver.resolveType(type, contextClass));
    }

    private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) throws IOException {
        try {
            ObjectMapper objectMapper = SpringContextUtil.getBean(ObjectMapper.class);
            return objectMapper.readValue(inputMessage.getBody(), javaType);
        } catch (MismatchedInputException ex) {  // specific kind of JsonMappingException
            throw new HttpMessageNotReadableException("Invalid JSON input: " + ex.getOriginalMessage(), ex, inputMessage);
        }
        catch (InvalidDefinitionException ex) {  // another kind of JsonMappingException
            throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
        }
        catch (JsonMappingException ex) {  // typically ValueInstantiationException
            throw new HttpMessageConversionException("JSON conversion problem: " + ex.getOriginalMessage(), ex);
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getOriginalMessage(), ex, inputMessage);
        }
    }


    @SneakyThrows
    @Override
    public Object read(Class<?> aClass, HttpInputMessage httpInputMessage) {
        JavaType javaType = getJavaType(aClass, null);
        Object o = readJavaType(javaType, httpInputMessage);
        Field[] fields = aClass.getDeclaredFields();
        FieldTypeProcesserHolder processerHolder = SpringContextUtil.getBean(FieldTypeProcesserHolder.class);
        Arrays.stream(fields).forEach(field -> {
            try {
                backToFrontProcess(field,o,processerHolder,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return o;
    }


    private void backToFrontProcess(Field field, Object o,FieldTypeProcesserHolder processerHolder, boolean backToFront) throws Exception{
        if (field != null && o != null){
            field.setAccessible(true);
            Class<?> type = field.getType();
            TimezoneConvert fieldAnnotation = field.getAnnotation(TimezoneConvert.class);
            if(fieldAnnotation != null){
                FieldTypeProcesser processer = processerHolder.getProcesser(field.getType());
                if (backToFront){
                    processer.backToFront(field,o,fieldAnnotation);
                } else {
                    processer.frontToBack(field,o,fieldAnnotation);
                }
            } else {
                // 不是基本类型和String类型，继续向下找
                Object o1 = field.get(o);
                Field[] declaredFields = type.getDeclaredFields();
                for (Field subField : declaredFields) {
                    if (o1 != null && o1.getClass().getDeclaredAnnotation(EnableTimezoneConvert.class) != null){
                        backToFrontProcess(subField,o1,processerHolder,backToFront);
                    }
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public void write(Object o, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Field[] fields = o.getClass().getDeclaredFields();
        FieldTypeProcesserHolder processerHolder = SpringContextUtil.getBean(FieldTypeProcesserHolder.class);
        Arrays.stream(fields).forEach(field -> {
            try {
                backToFrontProcess(field,o,processerHolder,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ObjectMapper objectMapper = SpringContextUtil.getBean(ObjectMapper.class);
        byte[] bytes = objectMapper.writeValueAsString(o).getBytes();
        httpOutputMessage.getBody().write(bytes);
        log.info("开始处理回文");
    }


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


    public static void main(String[] args) throws Exception{
        TimezoneDemo demo = new TimezoneDemo();
        demo.setChild(new TimezoneDemo());
        Field[] declaredFields = TimezoneDemo.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            String name = field.getName();
            if (Objects.equals(name,"value")){
                field.set(demo,"new Value");
            }
            Class<?> type = field.getType();
            type.isPrimitive();
            Object o = field.get(demo);
            Field[] declaredFields1 = o.getClass().getDeclaredFields();
        }
        System.out.println(JSON.toJSONString(demo));
    }




}
