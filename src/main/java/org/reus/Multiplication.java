/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus;

/**
 * @version $Id: Multiplication.java, v 0.1 2019-11-15 reus Exp $
 * @ClassName: Multiplication
 * @Description:不用乘法计算两个整数的乘法
 * @author: reus
 * 思路：
 * 1、两个整数乘法首先判断运算结果的符号
 * 2、将整数转化为二进制乘法运算
 * 具体操作：
 * 1、确定符号位，并用绝对值进行计算
 * 2、将被乘数转化为二进制并从右向左计算如果二进制位为1则加上乘数否则取0
 * 3、被乘数右移一位乘数左移一位，继续计算直到被乘数为0
 */
public class Multiplication {

    /**
     * 计算两个数的乘法
     * 采用的是二进制乘法
     * @param a
     * @param b
     * @return
     */
    public static int multiplication(int a, int b) {
        int sign = 1;
        if (b < 0) {
            sign = -sign;
            b = -b;
        }
        if (a < 0) {
            sign = -sign;
            a = -a;
        }
        int ret = 0;
        while (b > 0) {
            if ((b & 1) > 0) {
                ret = ret + a;
            }
            a <<= 1;
            b >>= 1;
        }
        if (sign < 0)
            return -ret;
        return ret;
    }

}