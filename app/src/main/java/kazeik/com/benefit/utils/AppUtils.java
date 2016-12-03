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
    public static String deviceMac = "";
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

    public static String submitCode = "overseasConsume/ReservevalidCode_tel.php";

//    public static String baseUrl = "http://wmfapp.yishengsz.net/";
public static String baseUrl = "http://10.0.0.192/";
    public static String versionPath = "http://wmfapp.yishengsz.net/enmu-web/";
    /**
     * 交易列表：  POST提交表单：mac        手机端          （POS机MAC地址）
     */
    public static String dealList = "overseasConsume/queryTradeDetail.php";

    /**
     * 授权绑定：  POST提交表单：mac                  （POS机MAC地址）
     * authorization        （授权码）
     */
    public static String authorizationBind = "overseasConsume/authorization.php";

    /**
     * 我要收款：  POST提交表单：mac                  （POS机MAC地址）
     * certificateType      （证件类型【可不提交】）
     * cardholderName       （姓名）
     * cardNumber           （卡号）
     * serviceCode          （cvv码）
     * tradersPassword      （密码）
     * firsttrack           （磁条一轨内容）
     * secondtrack          （磁条二轨内容）
     * thirdtrack           （磁条三轨内容）
     * dollarSign           （币种【人民币、澳元、美元、港元、英镑等】）
     * validDate            （有效日期）
     * validCode            （短信验证码）
     * tradeChannel         （交易渠道）
     * deviceKind           （终端类别【POS机、一体机、手机】）
     * consumptionMoney     （金额）
     * regionCountry        （消费国家/地区【澳门、香港】）
     * bankname             （卡的银行名称【根据卡号自动获取相应银行名称】）
     */
    public static String gathering = "overseasConsume/ReserveOverseasConsume.php";

    /**
     * 授权检测http://wmfapp.yishengsz.net/
     */
    public static String checkAuth = "authorization/getauthorization.php";
    /**
     * 更新检测返回信息：{"capVerson":"2.0.4","capFileName":"cap-v2.0.4-201609300923.apk","capUpdateContent":"更新内容(2016年09月30日) 1、修正银联通道提示输入验证码的问题 "}
     */
    public static String checkVersion = "enmu-web/getmbVersionInfo.php";

    /**
     * 银行卡绑定：  POST提交表单：mac                  （POS机MAC地址）
     * cardNo               （卡号）
     * cardholderName       （姓名）
     * bankName             （开户行）
     * identityCard         （身份证号）
     */
    public static String bindCard = "overseasConsume/bindBankCardInfo.php";

    public static String ad = "ad/ad.php";

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
