/**
 * reus
 * Copyright (C), 2011 - 2019, reus.
 */
package org.reus.leetcode;

/**
 * @version $Id: MiddleNum.java, v 0.1 2019-11-22 reus Exp $
 * @ClassName: MiddleNum
 * @Description: 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2
 * 请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。
 * @author: reus
 */
public class MiddleNum {

    public static void main(String[] args) {
        int[] nums1 = new int[] { 5, 6 };
        int[] nums2 = new int[] { 2, 4 };
        System.out.println(median(nums1, nums2));

    }

    /**
     *
     * @param A
     * @param B
     * @return
     */
    public static double median(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i])
                iMin = iMin + 1; // i is too small
            else if (i > iMin && A[i - 1] > B[j])
                iMax = iMax - 1; // i is too big
            else { // i is perfect
                int maxLeft;
                if (i == 0)
                    maxLeft = B[j - 1];
                else if (j == 0)
                    maxLeft = A[i - 1];
                else
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                if ((m + n) % 2 == 1)
                    return maxLeft;
                int minRight;
                if (i == m)
                    minRight = B[j];
                else if (j == n)
                    minRight = A[i];
                else
                    minRight = Math.min(B[j], A[i]);
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }
}