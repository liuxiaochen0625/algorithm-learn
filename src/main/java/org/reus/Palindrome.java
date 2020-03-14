package org.reus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 最长回文子串
 * 采用Manacher算法
 *
 */
public class Palindrome {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(longestPalindrome(scanner.nextLine()));
    }

    public static String longestPalindrome(String str) {
        List<Character> s_new = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            s_new.add('#');
            s_new.add(str.charAt(i));
        }
        s_new.add('#');
        List<Integer> Len = new ArrayList<>();
        String sub = "";
        int sub_middle = 0;
        int sub_side = 0;
        Len.add(1);
        for (int i = 1; i < s_new.size(); i++) {
            if (i < sub_side) {
                int j = 2 * sub_middle - i;
                if (j >= 2 * sub_middle - sub_side && Len.get(j) <= sub_side - i) {
                    Len.add(Len.get(j));
                } else
                    Len.add(sub_side - i + 1);
            } else
                Len.add(1);
            while ((i - Len.get(i) >= 0 && i + Len.get(i) < s_new.size()) && (s_new.get(i - Len.get(i)) == s_new.get(i + Len.get(i))))
                Len.set(i, Len.get(i) + 1);
            if (Len.get(i) >= Len.get(sub_middle)) {
                sub_side = Len.get(i) + i - 1;
                sub_middle = i;
            }
        }
        sub = str.substring((2 * sub_middle - sub_side) / 2, sub_side / 2);
        return sub;
    }

}

