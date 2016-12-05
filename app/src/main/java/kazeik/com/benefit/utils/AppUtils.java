package kazeik.com.benefit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;

import java.util.Random;

/**
 * Created by Administrator on 2016/10/31.
 */

public class AppUtils {
    private static boolean debug = true;

    public static boolean isDebug() {
        return debug;
    }

    public static void showToast(Context context, int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void Logs(Class<?> cls, String msg) {
        if (debug)
            Log.e(cls.getSimpleName(), msg);
    }

    public static String menuList= "appmenu.php";
    public static String appindex = "appindex.php";
    public static String baseUrl = "http://appmenu.yishengsz.net/";
    public static String versionPath = "getmbVersionInfo.php";

    /*
 * 返回长度为【strLength】的随机数，在前面补0
 */
    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        String fixLenthString = String.valueOf(pross);
        return fixLenthString.substring(1, strLength + 1);
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static int getVersion(Activity activity) {
        try {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
