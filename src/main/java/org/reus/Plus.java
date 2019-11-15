/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus;

/**
 * @version $Id: Plus.java, v 0.1 2019-11-15 reus Exp $
 * @ClassName: Plus
 * @Description: 不用加法计算两个整数的加法
 * @author: reus
 *  思路：
 *  1、异或运算是无进位加法运算
 *  2、与运算是进位运算
 *  3、异或运算和与运算结合就是加法运算。
 *  具体操作：
 *  1、先计算进位运算并将其左移移位得到最高位进位
 *  2、进行异或运算
 *
 */
public class Plus {

    /**
     * 计算两个数的加法
     * @param a
     * @param b
     * @return
     */
    public int plusAndSub(int a, int b) {

        while (b > 0) {
            int carry = (a & b) << 1;//进位
            a = (a ^ b);//无符号加法
            b = carry;
        }
        return a;
    }

}