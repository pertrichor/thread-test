package tech.bluext.test.mode.tradition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 1. 四个线程使用同一个锁对象 , 所以同一时间点 , 只有一个线程获得锁 , 拥有执行权 , 其他三个线程处于阻塞状态 , 进入等锁池.
 * <p>
 * 2. 当一个线程调用condition.await()时 , 它完成了 释放锁&进入等待池 的操作 , 失去了cpu执行权的争夺资格.
 * <p>
 * 3. 当一个线程调用condition.await()时 , 它释放的锁将被等锁池中的其中一个线程取得 , 这个线程将获得执行资格 , 执行代码时 ,
 * 将改变临界资源的状态(如果未改变 , 则错误的逻辑可能会使程序发生死锁问题).
 * <p>
 * 4. 每次线程执行完成后调用condition.signalAll() , 这将唤醒等待池中的所有线程 , 使其全部进入等锁池 , 这时的线程是阻塞但不休眠的 ,
 * 最后释放锁时 , 这个锁将重新被等锁池的所有线程重新竞争取得.
 * <p>
 * 5. 可以理解为一个Condition对象对应一个等待池 , 如果有多个condition对象 , 则每个condition对象都拥有自己的等待池.
 * 调用ACondition对象的await()方法 , 线程a将进入ACondition对象对应的等待池. 此时 , 调用BCondition的signalAll()方法无法唤醒线程a.
 * 所以使用多个Condition对象时要注意不要发生无效唤醒的情况 , 否则所有线程将进入等待池 , 发生死锁.
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 9:28
 */
public class Main {
    public static void main(String[] args) {
        List<Object> basket = new ArrayList<>();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread producer_1 = new Thread(new Producer(basket, lock, condition));
        Thread producer_2 = new Thread(new Producer(basket, lock, condition));
        Thread consumer_1 = new Thread(new Consumer(basket, lock, condition));
        Thread consumer_2 = new Thread(new Consumer(basket, lock, condition));

        producer_1.setName("生产者一号");
        producer_2.setName("生产者二号");
        consumer_1.setName("消费者一号");
        consumer_2.setName("消费者二号");

        producer_1.start();
        producer_2.start();
        consumer_1.start();
        consumer_2.start();
    }
}
