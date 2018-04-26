package tech.bluext.test.concept._volatile;

/**
 * Description：执行
 *
 * @author : xutao
 *         Created_Date : 2018-04-25 11:04
 */
public class Main implements Runnable {

    public int i = 0;
    public boolean isRunning = true;// 会引发死循环
//    public volatile boolean isRunning = true;// 使用volatile关键字后 , 主线程修改了isRunning的值将立即刷新到主内存 , 并且强制子线程下次访问isRunning值时从主内存重新读取.

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        new Thread(main).start();
        Thread.currentThread().sleep(200L);// 让子线程有充分的时间启动 , 加载数据到本地内存 并 开启循环
        main.isRunning = false;// 此时修改isRunning值修改的是主线程的本地内存中的值 , 而非主内存的值 , 也无法改变子线程的本地内存中的值 , 所以子线程持续在循环
        Thread.currentThread().sleep(200L);
        System.out.println("循环: " + main.i + " 次");
        System.out.println("主线程执行完成..");// 因为主线程执行完成后 , 子线程还处于活跃状态 , 所以主线程并不会真正退出
    }

    @Override
    public void run() {
        while (isRunning) {
            i++;
//            System.out.println("1");
//            如果在循环体内加入输出语句 , 将可以正确退出循环.
//            猜想: 因为输出语句在逻辑上输出的应该是运算后的最新值 , 所以会强制让子线程的本地内存和主内存完成一次同步.
//            这时就把isRunning的最新值读入了子线程的本地内存. 下一次循环时便跳出了循环.
        }
        System.out.println("循环中止..");
    }
}
