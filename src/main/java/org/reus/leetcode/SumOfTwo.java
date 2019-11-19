/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.leetcode;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @version $Id: SumOfTwo.java, v 0.1 2019-11-19 reus Exp $
 * @ClassName: SumOfTwo
 * @Description: 给定两个链表分别代表两个非负整数，链表的每个结点分别分别存储整数的每位数字，
 * 且是逆序存储。求解这两个整数的和并以链表的形式返回计算的结果。
 * @author: reus
 */
public class SumOfTwo {

    public static void main(String[] args) {
        LinkedList<Integer> nodeA = new LinkedList<>(Arrays.asList(2, 3, 5, 9, 9));
        LinkedList<Integer> nodeB = new LinkedList<>(Arrays.asList(3, 7, 9));
        LinkedList<Integer> result = sumOfTwoNum(nodeA, nodeB);
        System.out.println(result.toString());

    }

    public static LinkedList<Integer> sumOfTwoNum(LinkedList<Integer> nodeA,
                                                  LinkedList<Integer> nodeB) {
        LinkedList<Integer> result = new LinkedList<>();
        int carry = 0;
        int length = nodeA.size() > nodeB.size() ? nodeA.size() : nodeB.size();
        for (int i = 0; i < length; i++) {
            int temp;
            if (i < nodeA.size() && i < nodeB.size())
                temp = nodeA.get(i) + nodeB.get(i);
            else if (i < nodeA.size())
                temp = nodeA.get(i);
            else
                temp = nodeB.get(i);
            result.add((temp + carry) % 10);
            carry = (temp + carry) / 10;
        }
        while (carry > 0) {
            result.add(carry % 10);
            carry /= 10;
        }

        return result;
    }

}