package com.lgiter.practice.leetcode.rank;

import sun.security.util.Length;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给定几个数，拼接在一起，使得拼接出来的数最大
 * 例如： 1 23 3 41 50
 * 拼接结果是：50412331
 * 这里不能简单的理解为只比较每位数字的首个数字：
 * 因为45 和 4这种情况，很明显45 应该在高位。比较首个数字无法区分
 * 12 和 121 应该把12放到前面
 * 其实就是比较 a b 和 b a哪个大，也就是元素逐个排序
 */
public class LargestNumber {

    private class StringComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            String s1 = o1 + o2;
            String s2 = o2 + o1;
            return s2.compareTo(s1);
        }

    }

    private Long largestNum(int[] nums){
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }

        List<String> stringList = Arrays.asList(strs);
        stringList.sort(new StringComparator());
        String r = new String();
        for (int i = 0; i < stringList.size(); i++) {
            r += stringList.get(i);
        }
        return Long.valueOf(r);
    }

    public static void main(String[] args) {
        LargestNumber largestNumber = new LargestNumber();
        int[] num = new int[]{12,344,2322,545,7};
        System.out.println(largestNumber.largestNum(num));

        System.out.println(System.getenv("USER"));
    }

}
