package com.ljaer.interview.thread.volatiledemo;

import java.util.concurrent.TimeUnit;

/*
1 验证volatile的可见性
    1.1 加入int number=0，number变量之前根本没有添加volatile关键字修饰,没有可见性
    1.2 添加了volatile，可以解决可见性问题

int number = 0; 主线程会一直等待
volatile int number = 0; 主线程得到继续执行
*/
public class VolatileDemoA {
    public static void main(String[] args) {
        seeOkByVolatile();
    }

    //volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    private static void seeOkByVolatile() {
        MyDataA myData  = new MyDataA();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+ " \t come in");
            //暂停一会线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+ " \t updated number value: " +myData.number);
        },"AAA").start();

        //第2个线程就是我们的main线程
        while(myData.number==0){
            //main 线程一直在这里等待循环，直到 number 值不再等于0
        }
        System.out.println(Thread.currentThread().getName()+ " \t mission is over");
    }
}

class MyDataA {
    int number = 0;
    //volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

}

