package tech.bluext.test.concurrenthashmap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 1. 由于ConcurrentHashMap内部采用了分段锁的设计 , 而SynchronizedMap和Hashtable则是以同一个对象为锁 ,<br/>
 * 这导致ConcurrentHashMap中进行put()操作中 , 只锁一段key , 其他锁依然可以被其他线程获取 , 从而得到更高的效率.<br/>
 * <p>
 * ps: 因为Map的结构是根据hash值分成多个桶 , 每个桶包含一个链表结构 , 当哈希值不同时 , 直接存储 ; <br/>
 * 当哈希值不同时 , 判断equals()方法结果 , 若equals()返回false , 则储存在链表中. 所以每个桶的操作其实是相对独立的.<br/>
 * 因此分段锁不会引起线程问题.
 * <p>
 * 2. 测试结果的对比 , 并非所有情况下 , ConcurrentHashMap的性能都拥有最佳性能.<br/>
 * 当key值范围较小时 , 如<1000时 , 三者的性能差距很小 , 甚至ConcurrentHashMap的性能要低于SynchronizedMap和Hashtable.<br/>
 * 当key值范围较大时 , 如>100000 , ConcurrentHashMap的性能远超SynchronizedMap和Hashtable.<br/>
 * 总得来说 , 耗时 ConcurrentHashMap < SynchronizedMap < Hashtable
 * <p>
 * 3. 多线程时 , 线程数量的大小影响不大 , 当使用单线程测试 , 反而暴露了ConcurrentHashMap中实现更复杂的弊端 , ConcurrentHashMap耗时最高.
 * <p>
 * 4. 在key值离散率高的并发环境下使用ConcurrentHashMap. 在key值离散率低的并发环境下使用SynchronizedMap.
 * <p>
 * 5. 使用happens-before原则分析ConcurrentHashMap的put()和get() , 由于get()没有锁 , 无法得出连贯的hb传递关系 , 所以当多线程同时
 * 调用put()和get() , get()可能得不到put()的新值. 同理 , clear() 和 迭代器的实现都体现了ConcurrentHashMap的弱一致性.这是效率和安全的权衡结果.
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 14:56
 */
public class Main {

    private static final int LATCH_SIZE = 100 * 10000;// 初始计数值 , put()调用总次数
    private static final int THREAD_SIZE = 10;// 并发线程数
    private static final int TEST_SIZE = 10;// 测试次数
    private static final int KEY_RANGE = 100000;// Key的取值范围

    public static void main(String[] args) throws InterruptedException {
        int testCnt = TEST_SIZE;
        long cTotal = 0;
        long sTotal = 0;
        long hTotal = 0;

        while (testCnt-- > 0) {
            long concurrentHashMapCost = performTest(THREAD_SIZE, new ConcurrentHashMap<>());
            long synchronizedMapCost = performTest(THREAD_SIZE, Collections.synchronizedMap(new HashMap<>()));
            long hashTableCost = performTest(THREAD_SIZE, new Hashtable<>());

            cTotal += concurrentHashMapCost;
            sTotal += synchronizedMapCost;
            hTotal += hashTableCost;

            System.out.print("SynchronizedMap 耗时: " + synchronizedMapCost + " ms | ");
            System.out.print("ConcurrentHashMap 耗时: " + concurrentHashMapCost + " ms | ");
            System.out.print("HashTable 耗时: " + hashTableCost + " ms | ");
            System.out.println();
            Thread.currentThread().sleep(200L);// 保证打印顺序
        }
        System.out.print("SynchronizedMap 平均耗时: " + sTotal / TEST_SIZE + " ms | ");
        System.out.print("ConcurrentHashMap 平均耗时: " + cTotal / TEST_SIZE + " ms | ");
        System.out.print("HashTable 平均耗时: " + hTotal / TEST_SIZE + " ms | ");
        Thread.currentThread().sleep(500L);// 保证打印顺序
    }

    public static long performTest(int threadSize, Map<String, Object> map) {
        long costMillis = 9999999999L;

        try {
            CountDownLatch latch = new CountDownLatch(LATCH_SIZE);

            // 创建线程
            List<Thread> threadList = new ArrayList<>();
            while (threadSize-- > 0) {
                Thread thread = new Thread(() -> {
                    while (latch.getCount() > 0) {
                        int randomNum = (int) Math.round(Math.random() * KEY_RANGE);
                        map.put(randomNum + "", randomNum);
                        latch.countDown();
                    }
                });
                threadList.add(thread);
            }

            // 开始计时
            long startMillis = System.currentTimeMillis();

            // 启动线程
            for (Thread thread : threadList) {
                thread.start();
            }

            // 执行结束后计算耗时
            latch.await();
            costMillis = System.currentTimeMillis() - startMillis;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return costMillis;
    }
}
