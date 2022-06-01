package com.lgiter.practice.ast.algorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: lixiaolong
 * Date: 2022/4/21
 * Desc:
 */
public class Recursion {

    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        set1.add(1);
        System.out.println(set1.containsAll(set2));
        System.out.println(set2.containsAll(set2));
    }

    // [1,2,3,[4,5],6]
    public static void transform(int arr){
        List<Tree> result = new ArrayList<Tree>();
    }


    @Data
    class Tree {
        Integer value ;
        List<Tree> children;
    }

}
