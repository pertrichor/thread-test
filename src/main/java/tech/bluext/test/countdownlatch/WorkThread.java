package tech.bluext.test.countdownlatch;

import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;

/**
 * Description：任务线程
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 14:11
 */
@AllArgsConstructor
public class WorkThread implements Runnable {

    private Worker worker;
    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        while (true) {// x 错误
//        while (countDownLatch.getCount() != 0) {// √ 正确
            worker.work();
            countDownLatch.countDown();
            System.out.println(worker.getName() + "完成了工作 , 此时计数器为: " + countDownLatch.getCount());
        }
    }
}
