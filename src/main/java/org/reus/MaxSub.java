/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus;

/**
 * @version $Id: MaxSub.java, v 0.1 2019-11-15 reus Exp $
 * @ClassName: MaxSub
 * @Description: 最大连续整数和
 * @author: reus
 * 描述：给出一个整数数组，取出这个数组中相邻的数据和最大的那个子序
 * 思路：
 * 1、遍历整个数组(一次)
 * 2、记录到当前位置子序的和，如果大于最大值，则将最大子序的起始位置设置为当前子序起始位置，最大子序的终止位置为当前位置
 * 如果当前子序和小于0，则更换当前子序起始位置为当前位置+1并将当前子序和记为0
 */
public class MaxSub {

    public static void main(String[] args) {
        int[] num = new int[] { -2, 11, -4, 13, -5, -2 };
        int[] num2 = new int[] { -10, 1, 2, 3, 4, -5, -23, 3, 7, -21 };
        int[] nums = new int[] { 5, -8, 3, 2, 5, 0 };
        int[] num4 = new int[] { -2, 11, -4, 13, -5, 2, -5, -3, 12, -9 };
        getmax(num);
        System.out.println(maxSubsequenceSum4(num));
    }

    public static void getmax(int[] num) {
        int s = 0;//最大子序起始位置
        int e = 0;//最大子序终止位置
        int max = 0;//最大子序和
        int temp = 0;//当前子序和
        int ts = 0;//当前子序起始位置
        for (int i = 0; i < num.length; i++) {
            temp = temp + num[i];
            if (temp < 0) {
                ts = i + 1;
                e = i + 1;
                temp = 0;
            } else {
                if (temp > max) {
                    s = ts;
                    e = i;
                    max = temp;
                }
            }
        }
        System.out.println("maxsum=" + max + ",start:" + s + ",end=" + e);
    }

    public static int maxSubsequenceSum4(int a[]) {
        int thisSum, maxSum, i;
        thisSum = maxSum = 0;
        for (i = 0; i < a.length; i++) {
            thisSum += a[i];
            if (thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {
                thisSum = 0;
            }
        }
        return maxSum;
    }
}