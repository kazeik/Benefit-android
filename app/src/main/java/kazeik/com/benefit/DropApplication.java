package kazeik.com.benefit;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kazeik.chen , QQ:77132995,email:kazeik@163.com
 *         2016 10 29  17:24
 *         类说明:
 */
public class DropApplication extends Application {
    public List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void exit() {
        for (Activity a : activityList) {
            a.finish();
        }
    }

    public void add(Activity act) {
        activityList.add(act);
    }
}
