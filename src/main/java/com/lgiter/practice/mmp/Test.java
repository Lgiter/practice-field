package com.lgiter.practice.mmp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("static-access")
public class Test {

    private String abc;

    public static Map test(String ss, String sss) {


        try {
            Thread.currentThread().sleep(2000l);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put(ss, sss);

        return map;
    }

    public static Test testThree(String set, String abc, String ecd, String jj, String cc, String zv, String sss, String sfsd) {

        try {
            Thread.currentThread().sleep(2000l);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Test test = new Test();
        test.abc = "ddd";

        return test;

    }


    public static void main(String[] args) throws Exception {

        Long begingTime = new Date().getTime();

        Map result = test("CC", "DD");
		Test result2 = testThree("set", result.get("CC").toString(), "ecd", "jj", "cc", "zv", "sss", "sfsd");

        System.out.println(" in main result1 = " + result);
		System.out.println(" in main result2 = " + result2);

        Long endTime = new Date().getTime();

        System.out.println("method endTime=" + (endTime - begingTime));

    }

}