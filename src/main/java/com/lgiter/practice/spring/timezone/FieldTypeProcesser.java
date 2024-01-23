package com.lgiter.practice.spring.timezone;

import com.lgiter.practice.spring.anno.TimezoneConvert;
import com.lgiter.practice.spring.enums.Timezone;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Author: lixiaolong
 * Date: 2024/1/18
 * Desc:
 */
@Component
public abstract class FieldTypeProcesser<T> {

    private final Type type;

    abstract LocalDateTime read(Field field, Object o, TimezoneConvert fieldAnnotation);

    public void backToFront(Field field, Object o, TimezoneConvert fieldAnnotation) throws IllegalAccessException {
        transform(field,o,fieldAnnotation,fieldAnnotation.backendTimezone(),fieldAnnotation.frontendTimezone());
    }

    public void frontToBack(Field field, Object o, TimezoneConvert fieldAnnotation) throws IllegalAccessException {
        transform(field,o,fieldAnnotation,fieldAnnotation.frontendTimezone(),fieldAnnotation.backendTimezone());
    }

    private void transform(Field field, Object o, TimezoneConvert fieldAnnotation, Timezone baseTimezone, Timezone targetTimezone) throws IllegalAccessException {
        LocalDateTime transform = read(field, o,fieldAnnotation);
        LocalDateTime dateTime = transform.atZone(ZoneId.of(baseTimezone.getValue())).withZoneSameInstant(ZoneId.of(targetTimezone.getValue())).toLocalDateTime();
        T t = write(dateTime,fieldAnnotation);
        field.set(o,t);
    }

    abstract T write(LocalDateTime localDateTime,TimezoneConvert fieldAnnotation);

    public Type getType(){
        return this.type;
    }

    private static Class<?> findParameterizedTypeReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        // 如果父类是Object 就没戏了
        if (Object.class == parent) {
            throw new IllegalStateException("Expected FieldTypeProcesser superclass");
        } else {
            // 如果父类是工具类本身 就返回否则就递归 直到获取到ParameterizedTypeReference
            return FieldTypeProcesser.class == parent ? child : findParameterizedTypeReferenceSubclass(parent);
        }
    }

    protected FieldTypeProcesser() {
        Class<?> parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(this.getClass());
        // 获取父类的泛型类 ParameterizedTypeReference<具体类型>
        Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
        // 必须是 ParameterizedType
        Assert.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
        ParameterizedType parameterizedType = (ParameterizedType)type;
        // 获取泛型的具体类型  这里是单泛型
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
        this.type = actualTypeArguments[0];
    }

}
