package com.ljaer.interview.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序
* LinkedBlockingQueue:是一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量高于ArrayBlockingQueue
* SynchronousQueue：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移出操作，否则插入操作一直处于
* 阻塞状态，吞吐量通常要高
*
* 1.队列
*
* 2.阻塞队列
*   2.1阻塞队列有没有好的一面
*   2.2不得不阻塞，你如何管理
*
*/
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception{
//        exceptionMethod();
//        specialValueMethod();
//        blockMethod();
        timeOutMethod();
    }

    /**
     * 超时组
     *
     * 当阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时后生产者线程会退出
     *
     * 插入 : offer(e,time,unit)
     * 移除 : poll(time,unit)
     * 检查 : 不可用
     */
    public static void timeOutMethod() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //往阻塞队列添加元素
        System.out.println(blockingQueue.offer("a",5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",5L, TimeUnit.SECONDS));

        //从阻塞队列取元素
        System.out.println(blockingQueue.poll(5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(5L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(5L, TimeUnit.SECONDS));
    }


    /**
     * 阻塞组
     *
     * 插入 : put     当阻塞队列满时，生产者线程继续往队列里 put 元素，
     *                队列会一直阻塞生产线程直到 put 数据 or 响应中断退出
     * 移除 : take    当阻塞队列空时，消费者线程试图从队列里 take 元素，队列会一直阻塞消费者线程直到队列可用
     * 检查 : 不可用
     */
    public static void blockMethod() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //往阻塞队列添加元素
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("x");


        //从阻塞队列取元素
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }


    /**
     * 特殊值组
     *
     * 插入 : offer   插入方法，成功 true ，失败 false
     * 移除 : poll    移除方法，成功返回队列的元素，队列里面没有就返回 null
     * 检查 : peek    检查方法，成功返回队列的元素，队列里面没有就返回 null
     */
    private static void specialValueMethod() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        //往阻塞队列添加元素
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        //从阻塞队列取元素
        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println(blockingQueue.peek());

    }

    /**
     * 异常组
     * 当阻塞队列满时，再往队列里 add 插入元素会抛 IllegalStateException: Queue full
     * 当阻塞队列空时，再往队列里 remove 移除元素会抛 NoSuchElementException
     *      插入 : add        Exception in thread "main" java.lang.IllegalStateException: Queue full
     *      移除 : remove     Exception in thread "main" java.util.NoSuchElementException
     *      检查 : element    Exception in thread "main" java.util.NoSuchElementException
     */
    private static void exceptionMethod() {
        //        List list = new ArrayList();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
//        往阻塞队列添加元素
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //System.out.println(blockingQueue.add("x"));

//        从阻塞队列取元素
        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

    }
}
