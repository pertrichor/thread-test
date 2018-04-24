package tech.bluext.test.mode.blockingqueue;

import lombok.AllArgsConstructor;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Description：生产者
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 13:54
 */
@AllArgsConstructor
public class Producer implements Runnable {

    private BlockingQueue<String> queue;

    @Override
    public void run() {
//        如果设置仅循环两次 , 可以清晰得观察到生产者停止生产后 , 消费者无资源可消费时阻塞线程的状态.
//        int i = 2;
//        while (i-- > 0) {
        while (true) {
            try {
                long millis = System.currentTimeMillis();

                Thread.currentThread().sleep(200L);// 模拟生产耗时
                String newStr = UUID.randomUUID().toString().substring(0, 6);
                queue.put(newStr);// 如果队列大小超过了最大容量 , 则会持续等待 , 直到容量下降到最大容量以下 , 最大容量默认为Integer.MAX_VALUE

                System.out.println(Thread.currentThread().getName() + " 生产了 [ " + newStr + " ] , 队列中库存: " + queue.size() + " 个资源 - MilliSecond: " + millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
