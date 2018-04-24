package tech.bluext.test.mode.blockingqueue;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;

/**
 * Description：消费者
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 13:54
 */
@AllArgsConstructor
public class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    @Override
    public void run() {
        while (true) {
            try {
                long millis = System.currentTimeMillis();

                Thread.currentThread().sleep(200L);// 模拟消费耗时
                String str = queue.take();// 如果队列为空 , 则会持续等待队列中加入新资源

                System.out.println(Thread.currentThread().getName() + " 消费了 [ " + str + " ] , 队列中剩余: " + queue.size() + " 个资源 - MilliSecond: " + millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
