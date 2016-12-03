package kazeik.com.benefit.utils;


import android.text.TextUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/11/1.
 */

public class HttpNetUtils {
    private static HttpNetUtils instance;
    private HttpHandler httpHandler;

    public static HttpNetUtils getInstance() {
        if (null == instance) {
            instance = new HttpNetUtils();
        }
        return instance;
    }

    public RequestParams postParams(String msg){
        RequestParams params = new RequestParams();
        try {
            params.setBodyEntity(new StringEntity(msg));
        } catch (UnsupportedEncodingException e) {
        }
        params.setContentType("application/json");
        return params;
    }


    public void requestNetData(HttpRequest.HttpMethod method,
                               RequestParams params, final String tag, final OnNetEventListener listener) {
        String url = AppUtils.baseUrl + tag;
        HttpUtils utils = new HttpUtils();
        utils.configCurrentHttpCacheExpiry(50);
        httpHandler = utils.send(method, url, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        if (null != listener)
                            listener.onNetError(tag, arg1, arg0);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (null != listener) {
                            String value = arg0.result;
                            if (TextUtils.isEmpty(value)) {
                                listener.onNetError(tag, value, null);
                            } else {
                                AppUtils.Logs(getClass(),value);
                                listener.onNetSuccess(tag, value);
                            }
                        }
                    }
                });

    }

    public void cancel() {
        if (null != httpHandler)
            httpHandler.cancel(true);
    }
}
