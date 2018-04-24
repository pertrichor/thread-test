package tech.bluext.test.mode.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 1. BlockingQueue内部封装了线程协作的逻辑 , 使用BlockingQueue完成 生产者-消费者 模式变得十分简单优雅.
 * <p>
 * 2. 值得注意的是 , BlockingQueue的同义API非常多 , 例如加入元素有offer(E) add(E) put(E)等 , 他们的内部实现有一些区别 , 例如是否抛出异常、是否阻塞綫程等 , 需根据实际需要选择.
 * <p>
 * 3. 示例中为了能直观得看到被生产和被消费的元素 , 使用了随机字符串标识元素本身.
 *
 * @author : xutao
 *         Created_Date : 2018-04-24 13:54
 */
public class Main {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>();

        Thread producer_1 = new Thread(new Producer(queue));
        Thread producer_2 = new Thread(new Producer(queue));
        Thread consumer_1 = new Thread(new Consumer(queue));
        Thread consumer_2 = new Thread(new Consumer(queue));

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
