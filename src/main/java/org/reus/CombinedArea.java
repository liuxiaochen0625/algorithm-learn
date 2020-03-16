/**
 * reus
 * Copyright (C), 2011 - 2020, reus.
 */
package org.reus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @version $Id: CombinedArea.java, v 0.1 2020-03-13 reus Exp $
 * @ClassName: CombinedArea
 * @Description: 合并区间
 * 给定⼀个按开始时间从⼩到⼤排序的时间区间集合，请将重叠的区间合并。时
 * 间区间集合⽤⼀个⼆维数组表示，⼆维数组的每⼀⾏表示⼀个时间区间（闭区
 * 间），其中 0 位置元素表示时间区间开始，1 位置元素表示时间区间结束。
 * 例 1：输⼊：[ [1, 3], [2, 6], [8, 10], [15, 18] ]
 * 返回： [ [1, 6], [8, 10], [15, 18]]
 * 解释：时间区间 [1, 3] 和 [2, 6] 有部分重叠，合并之后为 [1, 6]
 * 例 2：输⼊：[[1, 4], [4, 5]]
 * 返回：[[1, 5]]
 * 解释：时间区间[1，4] 和 [4，5]重叠了⼀个时间点，合并之后为 [1，5]
 * @author: reus
 */
public class CombinedArea {

    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null) {
            return null;
        }
        if (intervals.size() <= 1) {
            return intervals;
        }

        //对intervals排序
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        List<Interval> res = new ArrayList<>();
        Interval cur_interval = intervals.get(0);
        Interval next_interval = null;
        for (int i = 1; i < intervals.size(); i++) {
            next_interval = intervals.get(i);
            if (cur_interval.end < next_interval.start) {
                //不相交，不能合并
                res.add(cur_interval);
                cur_interval = next_interval;
            } else if (cur_interval.end >= next_interval.start
                       && cur_interval.end <= next_interval.end) {
                cur_interval.end = next_interval.end;
            }
        }
        res.add(cur_interval);
        return res;
    }

    public static void main(String[] args) {
        int[][] edges = new int[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (i == j)
                    edges[i][j] = 0;
                else
                    edges[i][j] = -1;

        edges[1][2] = 1;
        edges[2][1] = 1;
        edges[2][3] = 3;
        edges[3][2] = 3;
        //        edges[1][3] = 100;
        //        edges[3][1] = 100;
        int result = minPath(3, edges, 1, 2);
        System.out.println(result);
    }

    /**
     * 合并有序区间
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 1 || intervals.length == 0)
            return intervals;
        int[][] result = new int[intervals.length][];
        int start = intervals[0][0];
        int end = intervals[0][1];
        int pos = 0;
        for (int i = 1; i < intervals.length; i++) {
            int s = intervals[i][0];
            int e = intervals[i][1];
            if (s > end) {
                result[pos] = new int[2];
                result[pos][0] = start;
                result[pos][1] = end;
                pos++;
                start = s;
                end = e;
            } else {
                end = e;
            }
        }
        result[pos] = new int[2];
        result[pos][0] = start;
        result[pos][1] = end;
        return result;
    }

    /**
     * 校验缩写
     * @param word
     * @param abbr
     * @return
     */
    public boolean validWordAbbreviation(String word, String abbr) {
        // write your code here
        char[] words = word.toCharArray();
        char[] abbrs = abbr.toCharArray();

        int i = 0, j = 0;
        while (i < words.length && j < abbrs.length) {
            //若abbr中包含非前导0的数字
            if (Character.isDigit(abbrs[j]) && abbrs[j] != '0') {
                int times = 0;//当前数字，eg:"i12i"中的12
                int cnt = 1;
                while (j < abbrs.length && Character.isDigit(abbrs[j])) {
                    times = cnt * times + abbrs[j] - '0';
                    cnt = 10 * cnt;
                    j++;
                }
                i = i + times;
            } else { //否则直接按位判断字符
                if (words[i] != abbrs[j])
                    return false;
                i++;
                j++;
            }
        }

        if (i == words.length && j == abbrs.length)
            return true;
        return false;
    }

    public static int minPath(int n, int[][] edges, int start, int end) {
        int[][] result = allPath(n, edges);
        return result[start][end] != 0 ? result[start][end] : edges[start][end];
    }

    public static int[][] allPath(int n, int[][] edges) {
        int[][] result = new int[n + 1][n + 1];
        for (int k = 1; k <= n; k++)
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= n; j++) {
                    if (edges[i][j] == -1 && edges[i][k] != -1 && edges[k][j] != -1) {
                        edges[i][j] = edges[i][k] + edges[k][j];
                        result[i][j] = edges[i][k] | edges[k][j];
                    } else if (edges[i][j] != -1 && (edges[i][k] == -1 || edges[k][j] == -1))
                        continue;
                    else if (edges[i][j] > edges[i][k] + edges[k][j]) {
                        edges[i][j] = edges[i][k] + edges[k][j];
                        result[i][j] = edges[i][k] | edges[k][j];
                    }
                }
        return result;
    }

}

class Interval {
    int start;
    int end;

    Interval() {
    }

    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}