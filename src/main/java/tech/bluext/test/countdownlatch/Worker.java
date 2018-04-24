package tech.bluext.test.countdownlatch;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description：工人
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 14:09
 */
@Getter
@AllArgsConstructor
public class Worker {

    private String name;// 姓名
    private long workTime;// 工作时间

    public void work() {
        try {
            System.out.println(name + " 开始工作..");
            Thread.currentThread().sleep(workTime);// 模拟工作时间
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
