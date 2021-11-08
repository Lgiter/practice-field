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
    public void multiList() throws IOException, TemplateException {
        String mapString = "{\n" +
                "    \"mulit_api_test01\":{\n" +
                "        \"total\":{\n" +
                "            \"dataList\":[\n" +
                "                {\n" +
                "                    \"pdi_intime_rate\":\"99.9%\",\n" +
                "                    \"first_detect_intime_rate\":\"99.8%\",\n" +
                "                    \"urgent_delivery_rate\":\"12.5%\",\n" +
                "                    \"noappoint_detect_rate\":\"17.7%\",\n" +
                "                    \"stock_intime_rate\":\"93.9%\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"day\":{\n" +
                "            \"dataList\":[\n" +
                "                {\n" +
                "                    \"data_date\":\"01\",\n" +
                "                    \"pdi_intime_num\":13,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":11,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"02\",\n" +
                "                    \"pdi_intime_num\":22,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":21,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"03\",\n" +
                "                    \"pdi_intime_num\":30,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":27,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"04\",\n" +
                "                    \"pdi_intime_num\":77,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":72,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"05\",\n" +
                "                    \"pdi_intime_num\":101,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":90,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"06\",\n" +
                "                    \"pdi_intime_num\":77,\n" +
                "                    \"pds_late_num\":1,\n" +
                "                    \"pdi_intime_rate\":98.7,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":72,\n" +
                "                    \"first_detect_late_num\":1,\n" +
                "                    \"first_detect_intime_rate\":98.6,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"07\",\n" +
                "                    \"pdi_intime_num\":112,\n" +
                "                    \"pds_late_num\":1,\n" +
                "                    \"pdi_intime_rate\":99.1,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":99,\n" +
                "                    \"first_detect_late_num\":1,\n" +
                "                    \"first_detect_intime_rate\":99,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"08\",\n" +
                "                    \"pdi_intime_num\":181,\n" +
                "                    \"pds_late_num\":4,\n" +
                "                    \"pdi_intime_rate\":97.8,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":170,\n" +
                "                    \"first_detect_late_num\":4,\n" +
                "                    \"first_detect_intime_rate\":97.7,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"09\",\n" +
                "                    \"pdi_intime_num\":147,\n" +
                "                    \"pds_late_num\":2,\n" +
                "                    \"pdi_intime_rate\":98.7,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":136,\n" +
                "                    \"first_detect_late_num\":1,\n" +
                "                    \"first_detect_intime_rate\":99.3,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                },\n" +
                "                {\n" +
                "                    \"data_date\":\"10\",\n" +
                "                    \"pdi_intime_num\":84,\n" +
                "                    \"pds_late_num\":0,\n" +
                "                    \"pdi_intime_rate\":100,\n" +
                "                    \"avg_pdi_intime_rate\":99.5,\n" +
                "                    \"first_detect_intime_num\":60,\n" +
                "                    \"first_detect_late_num\":0,\n" +
                "                    \"first_detect_intime_rate\":100,\n" +
                "                    \"avg_first_detect_intime_rate\":99.7\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Map map = JSON.parseObject(mapString, Map.class);
        System.out.println(JSON.toJSONString(map));
        StringWriter stringWriter = new StringWriter();
        Template template = config.getTemplate("multilist.ftl");
        template.process(map, stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);

    }

}
