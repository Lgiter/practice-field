package com.lgiter.practice.freemarker;

import com.alibaba.fastjson.JSON;
import com.lgiter.practice.freemarker.bean.Animals;
import com.lgiter.practice.freemarker.bean.Student;
import com.lgiter.practice.freemarker.funtion.GroupByMethod;
import com.oracle.tools.packager.Log;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import com.sun.tracing.dtrace.ArgsAttributes;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: lixiaolong
 * @Description: freemarker学习
 * @Date: 2021/9/27
 */
@Slf4j
public class FreemarkerDemo {

    private static Configuration config = new Configuration(Configuration.VERSION_2_3_31);
    private static Map<String, Object> map = new HashMap<>();

    @Before
    public void before() {
        final ClassTemplateLoader loader = new ClassTemplateLoader(Hello.class, "/template");
        config.setTemplateLoader(loader);
        config.setSharedVariable("group_by",new GroupByMethod());
        map.clear();
    }


    @Test
    public void helloWorld() throws Exception {
        Template template = config.getTemplate("hello.ftl");
        map.put("content", "Hello World");
        StringWriter stringWriter = new StringWriter();

        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);
    }

    @Test
    public void list() throws Exception {
        List<Animals> animals = Arrays.asList(
                new Animals("狗1", 4),
                new Animals("狗2", 4),
                new Animals("狗3", 4)
        );
        map.put("animals",animals);
        StringWriter stringWriter = new StringWriter();
        Template template = config.getTemplate("list.ftl");
        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);
    }

    @Test
    public void macro() throws Exception {
        List<Animals> list = Arrays.asList(
                new Animals("狗1", 4),
                new Animals("狗2", 4),
                new Animals("狗3", 4)
        );
        map.put("animals",list);
        StringWriter stringWriter = new StringWriter();
        Template template = config.getTemplate("macro.ftl");
        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);
    }

    @Test
    public void groupby() throws Exception{
        Student s1 = new Student("S1", "B1","A1");
        Student s2 = new Student("S1", "B1","A1");
        Student s3 = new Student("S1", "B2","A1");
        Student s4 = new Student("S2", "B1","A2");
        Student s5 = new Student("S23", "B2","A2");
        Student s6 = new Student("S2", "B3","A2");
        Student s8 = new Student("S1", "B1","A3");
        Student s9 = new Student("S1", "B1","A4");
        Student s10 = new Student("S9", "B1","A4");
        Student s11 = new Student("S10", "B1","A5");
        s10.setGrade2("L");
        s11.setGrade2("L");
        s9.setGrade2("L");
        List<Student> list = Arrays.asList(s1, s2, s3, s4, s5, s6, s8,s9,s10,s11);

        map.put("list",list);
        StringWriter stringWriter = new StringWriter();
        Template template = config.getTemplate("mygroupby.ftl");
        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);
    }

    @Test
    public void cxoTest() throws Exception{
        String mapString = "{\"results\":{\"A\":{\"frames\":[{\"schema\":{\"refId\":\"A\",\"meta\":{\"executedQueryString\":\"select\\ncount(id) as '问题输入',\\ncount(if(similarity\\u003e=0.67,id,null)) as '反馈条数',\\nconcat(round(count(if(similarity\\u003e=0.67,id,null))/count(id)*100,2),'%') as '回复率'\\nfrom robot_api_user_intention_supervision\\nWHERE yearweek(date_format(create_time,'%Y-%m-%d'))=yearweek(now())\"},\"fields\":[{\"name\":\"问题输入\",\"type\":\"number\",\"typeInfo\":{\"frame\":\"int64\",\"nullable\":true}},{\"name\":\"反馈条数\",\"type\":\"number\",\"typeInfo\":{\"frame\":\"int64\",\"nullable\":true}},{\"name\":\"回复率\",\"type\":\"string\",\"typeInfo\":{\"frame\":\"string\",\"nullable\":true}}]},\"data\":{\"values\":[[79],[53],[\"67.09%\"]]}}]}}}";


        Map map = JSON.parseObject(mapString, Map.class);
        System.out.println(JSON.toJSONString(map));
        StringWriter stringWriter = new StringWriter();
        Template template = config.getTemplate("cxotest.ftl");
        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);

    }

}
