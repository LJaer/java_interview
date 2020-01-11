package com.ljaer.interview.oom;

import java.util.concurrent.TimeUnit;

/**
 * java.lang.OutOfMemoryError:unbale to create new native thread
 *
 * linux下操作
 * javac -d . UnableCreateNewThreadDemo.java
 * java com.ljaer.interview.oom.UnableCreateNewThreadDemo
 *
 * 高并发请求服务器时，经常出现如下异常：java.lang.OutOfMemoryError:unbale to create new native thread
 * 准确的讲该native thread异常与对应的平台有关。
 *
 * 导致原因：
 * 应用创建了太多线程，一个应用进程创建多个线程，超过系统承载极限。
 * 服务器并不允许应用程序创建那么多线程，linux系统默认允许单个进程可以创建的线程数是1024个，如果应用创建超过这个数量，就会报java.lang.OutOfMemoryError:unable to create new native thread
 *
 * 解决办法：
 * 想办法降低应用程序创建线程的数量，分析应用是否真的需要创建那么多线程，如果不是，改代码将线程数降到最低。
 * 对于有的应用，确实需要创建多个线程，远超过linux系统默认的1024个线程的限制，可以通过修改linux服务器配置，扩大linux默认限制。
 */
public class UnableCreateNewThreadDemo {

    public static void main(String[] args) {
        for (int i=1;;i++){
            System.out.println("******************* i = " +i);

            new Thread(()->{
                try {TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);} catch (InterruptedException e) { e.printStackTrace();}
            },""+i).start();
        }
    }
}
