package com.lz.springbootjwt.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzj
 * @create 2019-07-26 18:06
 */
public class StringUtil {
    //public static String

    public static List<Integer> string2Integer(String str){
        ArrayList<Integer> integers = new ArrayList<>();
        String[] strs = str.split(",");
        for(int i=0;i<strs.length;i++){
            integers.add(Integer.parseInt(strs[i]));
        }
        return integers;
    }
}
