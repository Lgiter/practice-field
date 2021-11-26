package com.lgiter.practice.leetcode.window;

import org.springframework.util.StringUtils;

/**
 * 最小覆盖子串
 * @Author: lixiaolong
 * @Description:
 * @Date: 2021/11/26
 */
public class SmallestSubString {
    public static void main(String[] args) {
        System.out.println(solution("ADBBBDDCCSDABCDDSSC","ABC"));
    }
    private static String solution(String s1, String s2){
        if (!StringUtils.hasLength(s1) || !StringUtils.hasLength(s2))
            return "";

        int windowLength = s2.length();
        int left = 0,right = left + windowLength;
        while (windowLength <= s1.length()){
            while (right <= s1.length()){
                String substring = s1.substring(left, right);
                if (isContains(substring,s2)){
                    return substring;
                } else {
                    left ++;
                    right ++;
                }
            }
            windowLength ++;
            left = 0;
            right = left + windowLength;
        }
        return "";
    }
    private static boolean isContains(String s1,String s2){
        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            if (!s1.contains(c + "")){
                return false;
            }
        }
        return true;
    }
}
