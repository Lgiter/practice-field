package com.lgiter.practice.freemarker.funtion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.DefaultArrayAdapter;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author: lixiaolong
 * @Description: 自定义分组函数
 * @Date: 2021/9/27
 */
@Slf4j
public class GroupByMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {

        log.info(arguments.toString());
        if (arguments == null || arguments.size() < 2){
            throw new RuntimeException("参数长度有误");
        }
        if (arguments.size() > 4){
            throw new RuntimeException("分组字段数量不能超过4");
        }
        Object o = arguments.get(0);
        WrapperTemplateModel model =  null;
        if (o instanceof WrapperTemplateModel){
            model = (WrapperTemplateModel) o;
        } else {
            throw new RuntimeException("不支持的数据类型:" + o);
        }
        Object target = JSON.toJSONString(model.getWrappedObject());
        log.info(target.toString());
        int fieldNum = arguments.size() - 1;
        List<Object> result = new ArrayList<>();
        switch (fieldNum){
            case 1:
                result.add(JSON.toJSONString(oneLevel(target,arguments.get(1).toString())));
                return result;
            case 2:
                result.add(JSON.toJSONString(twoLevel(target,arguments.get(1).toString(),arguments.get(2).toString())));
                return result;
            case 3:
                result.add(JSON.toJSONString(threeLevel(target,arguments.get(1).toString(),arguments.get(2).toString(),arguments.get(3).toString())));
                return result;
            default:
        }
        return null;
    }

    private Map<Object,List<Object>> oneLevel(Object target,String field1){
        JSONArray list = JSON.parseArray(target.toString());
        Map<Object, List<Object>> collect = list.stream().collect(Collectors.groupingBy(o -> getByField(o,field1)));
        return collect;
    }

    private Map<Object,Map<Object,List<Object>>> twoLevel(Object target,String field1,String field2){
        JSONArray list = JSON.parseArray(target.toString());
        return list.stream().collect(Collectors.groupingBy(o -> getByField(o,field1),
                Collectors.groupingBy(o -> getByField(o,field2))));
    }

    private Map<Object,Map<Object,Map<Object,List<Object>>>> threeLevel(Object target,String field1,String field2,String field3){
        JSONArray list = JSON.parseArray(target.toString());
        return list.stream().collect(Collectors.groupingBy(o -> getByField(o,field1),
                Collectors.groupingBy(o -> getByField(o,field2),
                Collectors.groupingBy(o -> getByField(o,field3)))));
    }

    private Object getByField(Object object,String field){
        return Optional.ofNullable(object)
                .map(o->JSON.parseObject(o.toString()))
                .map(o->o.get(field))
                .orElseThrow(() -> new RuntimeException("属性:"+ field + " 在列表中不存在"));
    }

}
