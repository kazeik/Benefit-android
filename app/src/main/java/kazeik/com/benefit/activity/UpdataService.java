package kazeik.com.benefit.activity;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import kazeik.com.benefit.R;
import kazeik.com.benefit.bean.VersionBean;
import kazeik.com.benefit.utils.AppUtils;
import kazeik.com.benefit.utils.SdcardUtils;

public class UpdataService extends Service {
    /**
     * //     * 安卓系统下载类
     * //
     **/
//    DownloadManager manager;
//    /**
//     * 接收下载完的广播
//     **/
//    DownloadCompleteReceiver receiver;
    int progress = 0;
    int notifyId = 10000;
    NotificationCompat.Builder mBuilder;
    public NotificationManager mNotificationManager;
    public Boolean indeterminate = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        initNotify();
    }

    /**
     * 初始化下载器
     **/
    private void initDownManager(VersionBean bean) {
//        // 设置下载后文件存放的位置
        SdcardUtils sdcardUtils = new SdcardUtils();
        final File file = new File(sdcardUtils.getSDCardPath() + "DropPay/"+bean.capFileName+".apk");
        if (file.exists())
            file.delete();

        HttpUtils utils = new HttpUtils();
        utils.download(AppUtils.versionPath+bean.capFileName, file.getAbsolutePath(), new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                indeterminate = true;
                mBuilder.setContentText("下载完成")
                        .setProgress(0, 0, false);
                mNotificationManager.cancel(notifyId);
                installAPK(Uri.fromFile(file));
            }

            @Override
            public void onFailure(HttpException e, String s) {
                indeterminate = true;
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                indeterminate = true;
                progress = (int) (current * 100 / total);
//                setNotify(progress);
                showCustomProgressNotify(progress + "%");
            }

            @Override
            public void onStart() {
                super.onStart();
                showProgressNotify();
            }
        });
    }

    /**
     * 显示带进度条通知栏
     */
    public void showProgressNotify() {
        mBuilder.setContentTitle("正在下载")
                .setTicker("开始下载");
        if (indeterminate) {
            //不确定进度的
            mBuilder.setProgress(0, 0, true);
        } else {
            //确定进度的
            mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果
        }
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            VersionBean data = (VersionBean) intent.getSerializableExtra("data");
            if (null != data) {
                // 调用下载
                initDownManager(data);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 显示自定义的带进度条通知栏
     */
    private void showCustomProgressNotify(String status) {
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_progress);
        mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.mipmap.ic_launcher);
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, getString(R.string.app_name));
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
        if (progress >= 100) {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
        } else {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
        }
        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(0))
                .setTicker("更新");
        Notification nitify = mBuilder.build();
        nitify.contentView = mRemoteViews;
        mNotificationManager.notify(notifyId, nitify);
    }

    public void setNotify(int progress) {
        mBuilder.setProgress(100, progress, false);
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setWhen(System.currentTimeMillis())
                .setContentIntent(getDefalutIntent(0))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 安装apk文件
     */
    private void installAPK(Uri apk) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(apk, "application/vnd.android.package-archive");
        startActivity(i);
        stopSelf();
    }
}