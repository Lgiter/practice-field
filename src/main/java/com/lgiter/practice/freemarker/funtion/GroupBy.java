package com.lgiter.practice.freemarker.funtion;

import com.alibaba.fastjson.JSON;
import com.lgiter.practice.freemarker.bean.Student;
import lombok.Data;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lixiaolong
 * @Description: test
 * @Date: 2021/9/28
 */
@Data
public class GroupBy<T> {

    private T t;

//    public GroupBy(T t) {
//        this.t = t;
//    }

    public static void main(String[] args) {
        Student s1 = new Student("s1", "g50");
        Student s2 = new Student("s2", "g40");
        Student s3 = new Student("s3", "g40");
        Student s4 = new Student("s4", "g80");
        Student s5 = new Student("s5", "g4");
        Student s6 = new Student("s6", "g1");
        Student s8 = new Student("s6", "g1");
        Student s9 = new Student("s6", "g1");
        Student s10 = new Student("s8", "g2");
        Student s11 = new Student("s7", "g2");

        List<Object> list = Arrays.asList(s1, s2, s3, s4, s5, s6, s8,s9,s10,s11);
        List<String> strings = Arrays.asList("group", "name");
        GroupBy<Student> groupBy = new GroupBy<>();
        Map<List<String>, List<Object>> listListMap = groupBy.DynamicGroupListByFiled(list, new String[]{"group", "name"});
        System.out.println(JSON.toJSONString(listListMap));

    }

    //多字段排序
    public   <T> Map<List<String>, List<T>> DynamicGroupListByFiled(List<T> data, String[] groupByFieldNames) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        List<MethodHandle> handles =
                Arrays.stream(groupByFieldNames)
                        .map(field -> {
                            try {
                                return lookup.findGetter(this.getT().getClass(), field, String.class);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList());
        return data.stream().collect(Collectors.groupingBy(
                d -> handles.stream()
                        .map(handle -> {
                            try {
                                return (String) handle.invokeExact(d);
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList())
        ));
    }


    public static Map<List<Object>, List<Object>> groupListBy(List<Object> data, Function<Object, ?> mandatory, Function<Object, ?>... others) {
        return data.stream()
                .collect(Collectors.groupingBy(cl -> Stream.concat(Stream.of(mandatory), Stream.of(others)).map(f -> f.apply(cl)).collect(Collectors.toList())));
    }

    public static Object groupListBy2(List<Object> data,Function<Object, ?>... others) {
       return data.stream().collect(Collectors.groupingBy(
                c -> Stream.of(others).map(f -> f.apply(c))
                        .collect(Collectors.toList())));
    }



    public static <T, K, D, A, M extends Map<K, D>> Collector<T,?,M> groupby(Function<? super T, ? extends K> classifier,
                                                                             Supplier<M> mapFactory,
                                                                             Collector<? super T, A, D> downstream){



        return null;
    }

    public Object m1(){

        return null;
    }



}
