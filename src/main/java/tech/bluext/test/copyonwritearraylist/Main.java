package tech.bluext.test.copyonwritearraylist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description：执行
 * <p>
 * 总结:<br/>
 * 1. ArrayList的add方法由于是非线程安全的 , 在添加元素时 , size值可能已被其他线程篡改 , 所以会出现元素为空的情况
 * <p>
 * 2. CopyOnWriteArrayList的add方法会复制自己内部的数组 , 添加完元素后 , 将修改后的副本赋值为内部数组 , 过程中有锁控制
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 15:07
 */
public class Main {

    private static final int THREAD_POOL_SIZE = 2;

    public static void main(String[] args) {
        List<Object> unsafeList = new ArrayList<>();
        List<Object> safeList = new CopyOnWriteArrayList<>();

        ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        es.execute(new TaskTread(safeList));
        es.execute(new TaskTread(safeList));

        es.shutdown();
    }
}
