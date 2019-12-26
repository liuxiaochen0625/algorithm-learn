/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $Id: MaxHuiWenString.java, v 0.1 2019-11-26 reus Exp $
 * @ClassName: MaxHuiWenString
 * @Description: 给定一个字符串s，找出其中最长回文子串，假设给定的字符串最大长度为1000
 * @author: reus
 */
public class MaxHuiWenString {

    public static void main(String[] args) {
        String a = "abcbaaaaaa";
        System.out.println(longestPalindromeComplex(a));
        System.out.println(longestPalindromeComplex2(a));
        System.out.println(longestPalindromeSimple(a));
    }

    /**
     * 求解过程中分别以每个元素为中间元素（奇数为最中间的一个数，偶数为中间元素的其中一个）
     * ，同时从左右出发，计算出最长的回文子串
     * @param s
     * @return
     */
    public static String longestPalindromeComplex(String s) {

        int startIndex;
        int endIndex;
        int resStart = 0;
        int resEnd = 0;
        int maxLen = 0;
        int countLen;
        for (int i = 0; i < s.length(); i++) {
            startIndex = i;
            endIndex = i;
            countLen = 0;
            while (startIndex >= 0 && endIndex < s.length()) {
                if (s.charAt(startIndex) == s.charAt(endIndex)) {
                    if (startIndex == endIndex) {
                        countLen++;
                    } else {
                        countLen += 2;
                    }

                    startIndex--;
                    endIndex++;
                } else {
                    break;
                }
            }

            // 当前回文子串大于上一次最大回文子串
            if (countLen > maxLen) {
                maxLen = countLen;
                resStart = startIndex + 1;
                resEnd = endIndex - 1;
            }

            /* 回文子串为奇串处理 */
            startIndex = i - 1;
            endIndex = i;
            countLen = 0;
            while (startIndex >= 0 && endIndex < s.length()) {
                if (s.charAt(startIndex) == s.charAt(endIndex)) {
                    if (startIndex == endIndex) {
                        countLen++;
                    } else {
                        countLen += 2;
                    }

                    startIndex--;
                    endIndex++;
                } else {
                    break;
                }
            }

            // 当前回文子串大于上一次最大回文子串
            if (countLen > maxLen) {
                maxLen = countLen;
                resStart = startIndex + 1;
                resEnd = endIndex - 1;
            }
        }
        return s.substring(resStart, resEnd + 1);
    }

    public static String longestPalindromeComplex2(String s) {
        int n = s.length();
        boolean[][] f = new boolean[n][];
        for (int i = 0; i < f.length; i++)
            f[i] = new boolean[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                f[i][j] = false;
        int maxLen = 1;
        int start = 0;
        for (int i = 0; i < n; i++) {
            f[i][i] = true;
            for (int j = 0; j < i; j++) {
                f[j][i] = (s.charAt(j) == s.charAt(i) && (i - j < 2 || f[j + 1][i - 1]));
                if (f[j][i] && maxLen < (i - j + 1)) {
                    maxLen = i - j + 1;
                    start = j;
                }
            }
        }

        return s.substring(start, maxLen);
    }

    public static String longestPalindromeSimple(String s) {
        List<Character> s_new = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            s_new.add('#');
            s_new.add(s.charAt(i));
        }
        s_new.add('#');
        List<Integer> Len = new ArrayList<>();
        int sub_midd = 0;//表示在i之前所得到的Len数组中的最大值所在位置
        int sub_side = 0;//表示以sub_midd为中心的最长回文子串的最右端在S_new中的位置
        Len.add(1);
        for (int i = 1; i < s_new.size(); i++) {
            if (i < sub_side) {//i < sub_side时，在Len[j]和sub_side - i中取最小值，省去了j的判断
                int j = 2 * sub_midd - i;
                if (j >= 2 * sub_midd - sub_side && Len.get(j) <= sub_side - i) {
                    Len.add(Len.get(j));
                } else
                    Len.add(sub_side - i + 1);
            } else//i >= sub_side时，从头开始匹配
                Len.add(1);
            while ((i - Len.get(i) >= 0 && i + Len.get(i) < s_new.size())
                   && (s_new.get(i - Len.get(i)) == s_new.get(i + Len.get(i))))
                Len.set(i, Len.get(i) + 1);//s_new[i]两端开始扩展匹配，直到匹配失败时停止
            if (Len.get(i) >= Len.get(sub_midd)) {//匹配的新回文子串长度大于原有的长度
                sub_side = Len.get(i) + i - 1;
                sub_midd = i;
            }
        }
        return s.substring((2 * sub_midd - sub_side) / 2, sub_side / 2);//在s中找到最长回文子串的位置;

    }
}