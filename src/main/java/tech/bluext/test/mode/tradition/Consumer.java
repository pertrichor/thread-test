package tech.bluext.test.mode.tradition;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Description：消费者
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 9:19
 */
@AllArgsConstructor
public class Consumer implements Runnable {

    private List<Object> basket;
    private Lock lock;
    private Condition condition;

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();// 加锁

                while (basket == null || basket.size() == 0) {
                    System.out.println("消费者:" + Thread.currentThread().getName() + "正在等待生产..");
                    condition.await();// 释放锁 , 进入等待池
                }

                Thread.currentThread().sleep(100L);// 模拟消费消耗的时间
                basket.remove(0);// 消费首位元素 , 先生产的先消费
                System.out.println("消费者:" + Thread.currentThread().getName() + "完成一次消费.. 篮中还剩: " + basket.size() + " 个物品");

            } catch (Exception e) {
                System.out.println("消费者:" + Thread.currentThread().getName() + "发生异常..");
            } finally {
                condition.signalAll();// 唤醒等待池中的所有线程 , 进入等锁池
                lock.unlock();// 释放锁
            }
        }
    }
}

