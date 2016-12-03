package kazeik.com.benefit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;


import java.util.Date;

import butterknife.ButterKnife;
import kazeik.com.benefit.utils.HttpNetUtils;
import kazeik.com.benefit.utils.MyDateUtils;
import kazeik.com.benefit.view.OFHud;

/**
 * @author kazeik.chen , QQ:77132995,email:kazeik@163.com
 *         2016 10 29  16:23
 *         类说明:
 */
public abstract class BaseActivity extends FragmentActivity{
    private OFHud hud;
    DropApplication application;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(initLayout());
        ButterKnife.bind(this);
        application = (DropApplication) getApplication();
        application.add(this);
        initData();
    }

    public abstract int initLayout();
    public abstract void initData();


    /**
     * 带自定义提示文字无法按返回键取消的进度提示条
     *
     * @param message
     * @author kazeik.chen QQ:77132995 2015-3-5上午9:53:02 TODO kazeik@163.com
     */
    public void showHud(String message) {
        showHud(message, false);
    }

    /**
     * 无自定义提示文字无法按返回键取消的进度提示条
     *
     * @author kazeik.chen QQ:77132995 2015-3-5上午9:53:25 TODO kazeik@163.com
     */
    public void showHud() {
        showHud("");
    }

    /**
     * 无自定义提示文字的进度提示条
     *
     * @param isCancel 是否可以按返回键取消进度条显示
     * @author kazeik.chen QQ:77132995 2015-3-5上午9:53:55 TODO kazeik@163.com
     */
    public void showHud(boolean isCancel) {
        showHud("", isCancel);
    }

    public void showHud(String message, boolean isCancel) {
        if (hud == null) {
            hud = new OFHud(this);
            if (!TextUtils.isEmpty(message)) {
                hud.setMessage(message);
            } else {
                hud.setMessage(getString(R.string.loading));
            }
            hud.setCancelable(isCancel);
            hud.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancelHandler();
                }
            });
        }
        hud.show();
    }
    public void hideDialog() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Date time = MyDateUtils.getDateForStr(getString(R.string.end));
        long tempTime = time.getTime();
        if (System.currentTimeMillis() > tempTime) {
            application.exit();
            this.finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelHandler();
    }
    private void cancelHandler(){
        HttpNetUtils.getInstance().cancel();
        hideDialog();
    }
}
