package com.ljaer.interview.thread.volatiledemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 指令重排序(happen-before)
 * 一个简单的展示Happen-Before的例子.
 * 这里有两个共享变量:a和flag,初始值分别为0和false.在ThreadA中先给a=1,然后flag=true.
 * 如果按照有序的话,那么在ThreadB中如果if(flag)成功的话,则应该a=1,而a=a*1之后a仍然为1,下方的if(a==0)应该永远不会为真,永远不会打印.
 * 但实际情况是:在试验100次的情况下会出现0次或几次的打印结果,而试验1000次结果更明显,有十几次打印.
 */
public class SimpleHappenBefore {
    /** 这是一个验证结果的变量 */
    private static int a=0;
    /** 这是一个标志位 */
    private static boolean flag=false;

    public static void main(String[] args) throws InterruptedException {
        //由于多线程情况下未必会试出重排序的结论,所以多试一些次
        for(int i=0;i<1000;i++){
            ThreadA threadA=new ThreadA();
            ThreadB threadB=new ThreadB();
            threadA.start();
            threadB.start();
            //这里等待线程结束后,重置共享变量,以使验证结果的工作变得简单些.
            threadA.join();
            threadB.join();
            a=0;
            flag=false;
        }
    }

    static class ThreadA extends Thread{
        public void run(){
            a=1;
            flag=true;
        }
    }

    static class ThreadB extends Thread{
        public void run(){
            if(flag){
                a=a*1;
            }
            if(a==0){
                System.out.println("ha,a==0");
            }
        }
    }
}


