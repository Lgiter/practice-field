package com.lgiter.practice.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @Author: lixiaolong
 * @Description: JSON格式化输出
 * @Date: 2021/9/15
 */
public class JSONPrettyTest {

    public static void main(String[] args) {

        Person person = new Person("zhangsan",18);

        String s = new Gson().toJson(person);
        System.out.println(s);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(s);
        String prettyJSON = gson.toJson(je);
        System.out.println(prettyJSON);

    }


    static class Person{
        private String name;
        private Integer age;

        public Person() {
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
