package com.ljaer.interview.gc;

/**
 * 1-boolean 类型
 *
 *          默认参数是
 *          -XX:-PrintGCDetails
 *
 *          执行里设置以下参数
 *          -XX:+PrintGCDetails
 *
 *          C:\Users\LJaer>jps -l
 *          23088
 *          4896 com.ljaer.interview.gc.HelloGC
 *
 *          C:\Users\LJaer>jinfo -flag PrintGCDetails 4896
 *          -XX:-PrintGCDetails
 *
 * 2-kv设值类型
 * -XX:MetaspaceSize=128m
 * -XX:MaxTenuringThreshold=15
 *
 * C:\Users\LJaer>jinfo -flag MaxTenuringThreshold 17676
 * -XX:MaxTenuringThreshold=15
 *
 */
public class HelloGC {
    public static int oneAddone(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) throws InterruptedException {
        int res = oneAddone(1, 1);
        System.out.println(res);

        long totalMemory = Runtime.getRuntime().totalMemory();//返回java虚拟机中的内存总量
        long maxMemory = Runtime.getRuntime().maxMemory();//返回java虚拟机试图使用的最大内存量

        //默认
        // 初始内存是 PC 所在内存的 1/64
        // 最大内存是 PC 所在内存的 1/4
        //System.out.println("TOTAL_MEORY(-Xms) = " + totalMemory + " (字节) 、" + (totalMemory/(double)1024/1024) + "MB");
        //System.out.println("MAX_MEORY(-Xmx) = " + maxMemory + " (字节) 、" + (maxMemory/(double)1024/1024) + "MB");

//        Thread.sleep(Integer.MAX_VALUE);

        byte[] a = new byte[50*1024*1024];
    }
}
