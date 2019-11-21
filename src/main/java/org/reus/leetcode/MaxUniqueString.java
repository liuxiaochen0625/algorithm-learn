/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @version $Id: MaxUniqueString.java, v 0.1 2019-11-21 reus Exp $
 * @ClassName: MaxUniqueString
 * @Description: 给定一个字符串，找出其中最长没有重复子串的长度。
 * @author: reus
 */
public class MaxUniqueString {

    public static void main(String[] args) {
        String temp = "pwwkew";
        System.out.println(lengthOfLongestSubstringComplex(temp));
        System.out.println(lengthOfLongestSubstringSimple(temp));
    }

    /**
     * 解法一：暴力破解，三层循环
     * 1、第一层循环是从第一个字符开始遍历
     * 2、第二层循环是从第一层循环的下一个字符开始查看是否有重复的字符，若有则回到第一层重新开始，若没有比较是否是最大的字符串
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringComplex(String s) {
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                if (isUnique(s, i, j))
                    maxLen = maxLen < (j - i) ? (j - i) : maxLen;
                else
                    break;
            }
        }
        return maxLen;
    }

    public static int lengthOfLongestSubstringSimple(String s) {
        int maxLen = 0;
        //记录每个字符最后出现的位置，初始化为-1
        int[] last = new int[26];
        for (int i = 0; i < 26; i++)
            last[i] = -1;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            //当前元素重复
            if (last[s.charAt(i) - 'a'] >= start) {
                maxLen = maxLen > i - start ? maxLen : i - start;
                //移动当前位置到上一个重复元素的下一位
                start = last[s.charAt(i) - 'a'] + 1;
            }

            //记录第i个字符最后出现的位置
            last[s.charAt(i) - 'a'] = i;
        }
        System.out.println("最长不重复字符串开始位置:" + (start + 1));
        return maxLen;
    }

    /**
     * 遍历字符串从start到end是否有重复字符串
     * @param str
     * @param start
     * @param end
     * @return
     */
    private static boolean isUnique(String str, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            if (set.contains(str.charAt(i)))
                return false;
            set.add(str.charAt(i));
        }
        return true;
    }

}