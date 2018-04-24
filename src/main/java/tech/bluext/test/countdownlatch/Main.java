package tech.bluext.test.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 1. CountDownLatch在初始化时设置计数值 , 每次执行countDownLatch.countDown()时 , 计数值递减1 , 当计数值减为0 , 则将恢复countDownLatch.await()下代码的执行权.
 * <p>
 * 2. 理论上计数值应该等于等待执行的线程数 , 计数值只有一次设置机会 , 无法在后期更改.
 * <p>
 * 3. 如果计数值大于线程数 , 则当计数值减为0时 , countDownLatch.await()的阻塞将解除 , 无论线程是否已经执行完毕(示例代码演示的即是此类错误情况之一).
 * <p>
 * 4. 个人想法: 不一定必须按照 [线程数=计数值] 的思路设计程序.
 * 例如一个主线程需要为50名用户各自发送一条短信后再进行后续操作 , 由于服务器cpu资源有限 , 只能至多分配10个子线程处理 ,
 * 此时每个子线程执行5次发送短信任务 , 即执行5次countDownLatch.countDown() , 执行完50次后 , 主线程继续处理 , 这种使用情况我认为同样合理.
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 14:12
 */
public class Main {

    private static final int LATCH_CNT = 200;// 初始计数值

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(LATCH_CNT);

        new Thread(new WorkThread(new Worker("张三", 50L), countDownLatch)).start();
        new Thread(new WorkThread(new Worker("李四", 100L), countDownLatch)).start();

        try {
            countDownLatch.await();
            System.out.println("@@ 已经完成了所有工作..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
