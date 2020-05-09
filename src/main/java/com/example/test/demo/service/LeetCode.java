package com.example.test.demo.service;

import java.util.List;

public interface LeetCode {


    /**
     * 求解一个数组中和为x的所有组合  数组元素不可以重复使用, 数组中元素不重复
     */

    List<Integer> sumX(List<Integer> originList, Integer x);

    /**
     * 有x个台阶，可一次走一个也可以一次走两个，求解有多少种走法
     */
    Integer ladder(Integer x);

    /**
     * 判断一个字符串是否为回文字符串  ex: amdma
     */

    boolean isHuiwen(String str);


    /**
     * 一个1到n的有序数组，去掉其中的某一个数，打乱顺序，求解去掉了哪个数字,时间复杂度要求为O(n) 空间复杂度为O(1)
     */

    int findMissNum(int[] originList);

    /**
     * 翻转链表
     */


}
