package com.ljaer.interview.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*
题目：synchronized 和 lock 有什么区别？用新的 lock 有什么好处？ 你举例说说

1-原始构成
synchronized 是关键字属于 JVM 层面
        monitorenter (底层是通过 monitor 对象来完成，其实 wait/notify 等方法也依赖于 monitor 对象
        只有在同步块或方法中才能调用 wait/notify 等方法   )
        monitorexit

Lock 是具体类（java.util.concurrent.locks.Lock） 是 api 层面的类

2-使用方法
synchronized 不需要用户去手动释放锁，当 synchronized 代码执行完后系统会自动让线程释放对锁的占用

ReentrantLock 则需要用户去手动释放锁，若没有主动释放锁，就有可能出现死锁现象。

3-等待是否可中断
synchronized 不可中断，除非抛出异常或者正常运行完成

ReentrantLock 可中断，
    1-设置超时方法 tryLock(long timeout,TimeUnit unit)
    2-lockInterruptibly() 放代码块中，调用 interrupt() 方法可中断

4-加锁是否公平
synchronized 非公平锁
ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入 boolean 值， true 为公平锁， false 为非公平锁


5-锁绑定多个条件 Condition
synchronized 没有
ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不像 synchronized 要么随机唤醒一个线程要么全部唤醒
*/



/*
* 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
* A打印5次，B打印10次，C打印15次
* 紧接着
* A打印5次，B打印10次，C打印15次
* 。。。。。
* 打印10轮
* */
class ShareResource{
    private int number = 1;//A:1.B:2,C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            //1判断
            while(number != 1){
                c1.await();
            }
            //2干活
            for(int i=1;i<=5;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 2;
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            //1判断
            while(number != 2){
                c2.await();
            }
            //2干活
            for(int i=1;i<=10;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 3;
            c3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            //1判断
            while(number != 3){
                c3.await();
            }
            //2干活
            for(int i=1;i<=15;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3通知
            number = 1;
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args){
        ShareResource shareResource = new ShareResource();

        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print5();
            }
        },"A").start();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print10();
            }
        },"B").start();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                shareResource.print15();
            }
        },"C").start();
    }
}
