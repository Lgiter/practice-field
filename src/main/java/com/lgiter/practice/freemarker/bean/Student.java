package com.lgiter.practice.freemarker.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lixiaolong
 * @Description: 学生
 * @Date: 2021/9/27
 */
@Data
@AllArgsConstructor

public class Student {
    private String name;
    private String group;
    private String grade;
    private String grade2 = "ALL";

    public Student(String name, String group, String grade) {
        this.name = name;
        this.group = group;
        this.grade = grade;
    }

    public Student(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public void test (List<Student> list, String ... groupByField) throws Exception{
        Map<String, Map<String, List<Student>>> collect = list.stream().collect(Collectors.groupingBy(Student::getGroup,
                Collectors.groupingBy(Student::getName)));


         list.stream().collect(
                Collectors.groupingBy(Student::getGroup,
                    Collectors.groupingBy(Student::getName,
                        Collectors.groupingBy(Student::getGroup))));

    }

}
