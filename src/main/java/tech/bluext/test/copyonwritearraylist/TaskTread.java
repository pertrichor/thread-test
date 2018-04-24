package tech.bluext.test.copyonwritearraylist;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * Description：任务线程
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 15:05
 */
@AllArgsConstructor
public class TaskTread implements Runnable {
    private List<Object> list;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            list.add(Math.round(Math.random() * 10));
        }

        System.out.println(Arrays.toString(list.toArray()));
    }
}
