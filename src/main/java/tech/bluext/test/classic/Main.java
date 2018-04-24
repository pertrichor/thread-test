package tech.bluext.test.classic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 一个极简的多线程操作临界资源的示例. 由于Resource.increase()方法为非原子性的递增操作 , 所以不加锁时会发生线程问题.
 * 加上显示锁后 , 问题得到解决.
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 13:38
 */
public class Main {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Task(lock));
        Thread t2 = new Thread(new Task(lock));
        Thread t3 = new Thread(new Task(lock));

        t1.setName("t1");
        t2.setName("t2");
        t3.setName("t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
