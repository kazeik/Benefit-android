package kazeik.com.benefit.utils;

import com.lidroid.xutils.exception.HttpException;

/**
 * Created by Administrator on 2016/11/1.
 */

public interface OnNetEventListener {
    public void onNetSuccess(String tag, String body);
    public void onNetError(String tag, String errorMsg, HttpException ex);
}
