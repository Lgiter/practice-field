package com.lgiter.practice.spring.enums;

/**
 * Author: lixiaolong
 * Date: 2024/1/16
 * Desc:
 */
public enum Timezone {
    UTC1("UTC+01:00","东一区"),
    UTC2("UTC+02:00","东二区"),
    UTC3("UTC+03:00","东三区"),
    UTC4("UTC+04:00","东四区"),
    UTC5("UTC+05:00","东五区"),
    UTC6("UTC+06:00","东六区"),
    UTC7("UTC+07:00","东七区"),
    UTC8("UTC+08:00","东八区");

    private String value;

    private String desc;

    Timezone(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
