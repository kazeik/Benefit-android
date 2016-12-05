package kazeik.com.benefit.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kazeik.com.benefit.BaseActivity;
import kazeik.com.benefit.DropApplication;
import kazeik.com.benefit.R;
import kazeik.com.benefit.bean.MenuListModel;
import kazeik.com.benefit.bean.OnItemEventListener;
import kazeik.com.benefit.utils.AppUtils;
import kazeik.com.benefit.utils.HttpNetUtils;
import kazeik.com.benefit.utils.MyDateUtils;
import kazeik.com.benefit.utils.OnNetEventListener;
import kazeik.com.benefit.view.PopupWindowUtil;

public class MainActivity extends BaseActivity implements OnNetEventListener, OnItemEventListener {

    @Bind(R.id.ll_botton_view)
    LinearLayout llBottonView;
    @Bind(R.id.wv_view)
    WebView webView;
    LayoutInflater inflater;
    int width;
    DropApplication application;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        application = (DropApplication) getApplication();
        inflater = LayoutInflater.from(this);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDisplayZoomControls(true);
        setting.setSupportZoom(true);
        setting.setBuiltInZoomControls(true);
        setting.setUseWideViewPort(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setLoadWithOverviewMode(true);
        webView.loadUrl("http://www.gyzx.org/");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date time = MyDateUtils.getDateForStr(getString(R.string.end));
        long tempTime = time.getTime();
        if (System.currentTimeMillis() > tempTime) {
            application.exit();
            this.finish();
        }
    }

    private void getData() {
        showHud();
        HttpNetUtils.getInstance().requestNetData(HttpRequest.HttpMethod.GET, null, AppUtils.menuList, this);
    }

    @Override
    public void onNetSuccess(String tag, String body) {
        hideDialog();
        AppUtils.Logs(getClass(), body);
        try {
            List<MenuListModel> items = new Gson().fromJson(body, new TypeToken<List<MenuListModel>>() {
            }.getType());
            if (null != items) {
                final int subWidth = width / items.size();
                for (final MenuListModel item : items) {
                    RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.bottom_view, null);
                    TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu_text);
                    tvMenu.setText(item.item);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    view.setLayoutParams(params);
                    llBottonView.addView(view);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PopupWindowUtil util = new PopupWindowUtil();
                            util.dismissPopup();
                            util.showPopup(MainActivity.this, view, item, subWidth, MainActivity.this);
                        }
                    });
                }
            }
        } catch (Exception ex) {
            AppUtils.showToast(this, "服务器数据异常，请检查数据");
        }
    }

    @Override
    public void onNetError(String tag, String errorMsg, HttpException ex) {
        hideDialog();
        AppUtils.showToast(this, errorMsg);
    }

    @Override
    public void onItemEvent(String url) {
        webView.loadUrl(url);
    }
}
