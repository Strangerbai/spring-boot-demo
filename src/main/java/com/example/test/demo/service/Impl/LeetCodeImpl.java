package com.example.test.demo.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.test.demo.service.LeetCode;

import java.util.*;

public class LeetCodeImpl implements LeetCode {

    List<Integer> result = new ArrayList<>();

    @Override
    public List<Integer> sumX(List<Integer> originList, Integer x) {
        Collections.sort(originList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        Queue<Integer> aaa = new LinkedList<>();
        aaa.offer(1);
        aaa.poll();
        aaa.size();



        System.out.println(originList);
        return null;
    }

    @Override
    public Integer ladder(Integer x) {
        Integer result = 0;
        if(x == 0){
            return 0;
        } else if(x == 1){
            return 1;
        } else if(x == 2){
            return 2;
        }else{
            for(int i = x; i > 2; i--){
                result = ladder(x-1) + ladder(x-2);
            }
        }
        return result;
    }

    @Override
    public boolean isHuiwen(String str) {
        if(str.length() == 1){
            return true;
        }
        for(int i = 0;i<str.length()/2;i++){
            if(str.charAt(i) != str.charAt(str.length()-i-1)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int findMissNum(int[] originList) {
        for(int i = 0;i<originList.length;i++){
            while(originList[i] != i+1){
                if(originList[i] > originList.length-1){
                    break;
                }else{
                    int temp = originList[originList[i]-1];
                    originList[originList[i]-1] = originList[i];
                    originList[i] = temp;
                }
            }
        }
        for(int i = 0;i<originList.length;i++){
            if(originList[i] != i+1){
                return i+1;
            }
        }
        System.out.println(JSON.toJSONString(originList));
        return -1;
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(2);
        LeetCodeImpl leetCode = new LeetCodeImpl();
        leetCode.sumX(list, 3);
        Integer result = leetCode.ladder(5);
        System.out.println(result);
        System.out.println(leetCode.isHuiwen("amdma"));
        int miss = leetCode.findMissNum(new int[]{9,7,1,4,5,2,3,8});
        System.out.println(miss);
    }


}
