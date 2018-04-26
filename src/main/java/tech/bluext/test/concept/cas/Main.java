package tech.bluext.test.concept.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description：执行
 *
 * @author : xutao
 *         Created_Date : 2018-04-25 10:05
 */
public class Main {

    public volatile AtomicInteger i = new AtomicInteger(0);

    public void increase() {
        i.getAndIncrement();// 原子性操作 , 底层使用CAS实现
    }

    public static void main(String[] args) {
        Main main = new Main();
        CountDownLatch latch = new CountDownLatch(1000000);
        int i = 2;
        while (i-- > 0) {
            new Thread(() -> {
                while (latch.getCount() > 0) {
                    main.increase();
                    latch.countDown();
                    System.out.println("i: " + main.i + " - count: " + latch.getCount());
                }
            }).start();
        }

        try {
            latch.await();
            System.out.println("结果 i: " + main.i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
