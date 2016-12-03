package kazeik.com.benefit.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kazeik.com.benefit.BaseActivity;
import kazeik.com.benefit.R;
import kazeik.com.benefit.bean.MenuListModel;
import kazeik.com.benefit.utils.AppUtils;
import kazeik.com.benefit.utils.HttpNetUtils;
import kazeik.com.benefit.utils.OnNetEventListener;

public class MainActivity extends BaseActivity implements OnNetEventListener {

    @Bind(R.id.ll_botton_view)
    LinearLayout llBottonView;
    LayoutInflater inflater;
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        inflater = LayoutInflater.from(this);
        getData();
    }

    private void getData() {
        String url = "getMenuList.php";
        HttpNetUtils.getInstance().requestNetData(HttpRequest.HttpMethod.GET, null, url, this);
    }

    @Override
    public void onNetSuccess(String tag, String body) {
        List<MenuListModel> items = new Gson().fromJson(body, new TypeToken<List<MenuListModel>>() {
        }.getType());
        if(null != items){
            for (MenuListModel item :items) {
//                TextView textView = new TextView(this);
//                textView.setText(item.item);
//                textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
//                textView.setTextSize(15);
//                textView.setTextColor(Color.BLACK);
                RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.bottom_view,null);
                TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu_text);
                tvMenu.setText(item.item);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                view.setLayoutParams(params);
                llBottonView.addView(view);
            }
        }
    }

    @Override
    public void onNetError(String tag, String errorMsg, HttpException ex) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
