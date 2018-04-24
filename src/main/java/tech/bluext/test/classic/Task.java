package tech.bluext.test.classic;

import java.util.concurrent.locks.Lock;

/**
 * Description：任务体
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 13:36
 */
public class Task implements Runnable {

    private int CNT = 100000;
    private Lock lock;

    public Task(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while (CNT-- > 0) {
            try {
                lock.lock();
                Resource.increase();
                System.out.println(Thread.currentThread().getName() + " -- " + Resource.get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
