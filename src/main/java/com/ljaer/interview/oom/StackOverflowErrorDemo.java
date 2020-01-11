package com.ljaer.interview.oom;

/**
 * java.lang.StackOverflowError
 * 栈默认是 512k-1024k
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args){
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();//Exception in thread "main" java.lang.StackOverflowError
    }
}
