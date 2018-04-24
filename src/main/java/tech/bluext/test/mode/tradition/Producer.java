package tech.bluext.test.mode.tradition;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Description：生产者
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 9:25
 */
@AllArgsConstructor
public class Producer implements Runnable {

    private static final int MAX_SIZE = 100;

    private List<Object> basket;
    private Lock lock;
    private Condition condition;

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();// 加锁

                while (basket.size() >= MAX_SIZE) {
                    System.out.println("生产者:" + Thread.currentThread().getName() + "正在等待消费..");
                    condition.await();// 释放锁 , 进入等待池
                }

                Thread.currentThread().sleep(100L);// 模拟生产消耗的时间
                basket.add(new Object());// 置入新生产的对象
                System.out.println("生产者:" + Thread.currentThread().getName() + "完成一次生产..篮中还剩: " + basket.size() + " 个物品");

            } catch (Exception e) {
                System.out.println("生产者:" + Thread.currentThread().getName() + "发生异常..");
            } finally {
                condition.signalAll();// 唤醒等待池中的所有线程 , 进入等锁池
                lock.unlock();// 释放锁
            }
        }
    }
}
