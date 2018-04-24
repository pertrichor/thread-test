package tech.bluext.test.classic;

/**
 * Description：临界资源
 *
 * @author : xutao
 *         Created_Date : 2018-04-23 13:35
 */
public class Resource {

    private static int resource = 0;

    public static void increase() {
        resource = resource + 1;
    }

    public static int get() {
        return resource;
    }
}
