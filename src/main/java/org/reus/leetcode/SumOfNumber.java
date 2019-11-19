/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @version $Id: SumOfNumber.java, v 0.1 2019-11-19 reus Exp $
 * @ClassName: SumOfNumber
 * @Description: 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
 * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用
 * @author: reus
 *
 */
public class SumOfNumber {

    public static void main(String[] args) {
        int[] nums = new int[] { 2, 7, 11, -2 };
        int target = 9;
        int[] result = twoSumComplex(target, nums);
        for (int i = 0; i < result.length; i++)
            System.out.println(result[i]);
        result = twoSumSimple(target, nums);
        for (int i = 0; i < result.length; i++)
            System.out.println(result[i]);
    }

    /**
     * 解法一：暴力破解
     * 遍历两次数组
     * @param target
     * @param nums
     * @return
     */
    public static int[] twoSumComplex(int target, int nums[]) {
        int[] result = new int[nums.length];
        int pos = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (temp == nums[j]) {
                    result[pos++] = i;
                    result[pos++] = j;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 采用hash表的方式，只需遍历一次
     * @param target
     * @param nums
     * @return
     */
    public static int[] twoSumSimple(int target, int nums[]) {
        int[] result = new int[nums.length];
        Map<Integer, Integer> map = new HashMap<>();
        int pos = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp)) {
                result[pos++] = i;
                result[pos++] = map.get(temp);
            }
            map.put(nums[i], i);
        }
        return result;
    }
}