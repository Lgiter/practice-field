package com.lgiter.practice.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lixiaolong
 * @Description: helloworld
 * @Date: 2021/9/27
 */
public class Hello {

    public static void main(String[] args) throws Exception{

        Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        final ClassTemplateLoader loader = new ClassTemplateLoader(Hello.class, "/template");
        config.setTemplateLoader(loader);

        Template template = config.getTemplate("hello.ftl");
        Map<String,Object> map = new HashMap<>();
        map.put("content","Hello World");
        StringWriter stringWriter = new StringWriter();

        template.process(map,stringWriter);
        String s = stringWriter.getBuffer().toString();
        System.out.println(s);


    }
}
