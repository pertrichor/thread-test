package tech.bluext.test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description：测试任务
 *
 * @author : xutao
 *         Created_Date : 2018-05-08 15:08
 */
public class TestTask {

    private static AtomicInteger count = new AtomicInteger(0);

    private static int increase() {
        return count.incrementAndGet();
    }

    private static void reset() {
        count = new AtomicInteger(0);
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            this.batchTaskRun();
            System.out.println("当前count值: " + TestTask.count.get());
            TestTask.reset();
            System.out.println("批次 " + (i + 1) + " 执行完毕");
        }
        System.out.println("Task 主进程执行完毕");
    }

    private void batchTaskRun() throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(() -> {
                this.eachTaskRun();
            });
            t.start();
            list.add(t);
        }

        for (Thread t : list) {
            t.join();
        }
    }

    private void eachTaskRun() {
        int i = 1000000;
        while (i-- > 0) {
            if (i == 1) {
                TestTask.increase();
            }
        }
    }
}
