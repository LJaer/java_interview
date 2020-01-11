package com.ljaer.interview.oom;

import java.util.Random;

/**
 * -Xms1m -Xmx1m
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class JavaHeapSpaceDemo {
    public static void main(String[] args){

//        byte[] bytes = new byte[80*1024*1024];//80m

        String str = "LJaer";

        while(true){
            str += str + new Random().nextInt(11111111)+new Random().nextInt(22222222);
            str.intern();
        }

    }
}
