package com.ljaer.interview.thread.volatiledemo;

public class SingletonDemo {
    private static SingletonDemo instance = null;

    //private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造函数SingletonDemo（）");
    }

    //DCL(Double Check Lock双端检锁机制)
    /**
     * DCL（双端检锁）机制不一定线程安全，原因是有指令重排序的存在，加入volatile可以禁止指令重排
     * 原因在于某一个线程执行到第一个检测，读取到的instance不为null时，instance的引用对象可能没有完成初始化。
     * instance = new SingletonDemo();可以分为以下 3 步完成（伪代码）
     *
     * memory = allocate();  //1-分配对象内存空间
     * instance(memory);     //2-初始化对象
     * instance = memory;    //3-设置 instance 指向刚分配的内存地址，此时 instance != null
     *
     * 步骤 2 和步骤 3 不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并没有改变，
     * 囚此这种重排优化是允许的。
     *
     * memory = allocate();  //1-分配对象内存空间
     * instance = memory;    //3-设置 instance 指向刚分配的内存地址，此时 instance != null，但是对象还没有初始化完成！
     * instance(memory);     //2-初始化对象
     *
     * 指令重排只会保证串行语义的执行一致性（单线程），但并不会关心多线程间的语义一致性。
     *
     * 所以当一条线程访问instance不为null时，由于instance实例未必已初始化完成，也就造成了线程安全问题
     */
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        //不加 volatile 极小情况下会出问题，引用不为空，但是实际没值
        return instance;
    }

    public static void main(String[] args) {
//        单线程（main线程的操作）
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//

//        并发多线程后，情况发生了很大的变化
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
