package com.ljaer.interview.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
* 写一个自旋锁
* 自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞。
*
* 通过 CAS 操作完成自旋锁，
* A 线程先进来调用 myLock 方法自己持有锁 5 秒钟，
* B 随后进来发现当前有线程持有锁，不是 null， 所以只能通过自旋等待，直到 A 释放锁后 B 抢到
* */
public class SpinLockDemo {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in ");
        while(!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock()");
    }

    public static void main(String[] args){
        //        原子引用线程
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace();}
            spinLockDemo.myUnLock();
        },"AA").start();

        try{ TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}

        new Thread(()->{
            spinLockDemo.myLock();
            try{ TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace();}
            spinLockDemo.myUnLock();
        },"BB").start();

        while (Thread.activeCount()>2){
            Thread.yield();
        }
    }
}
