package kazeik.com.benefit.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import kazeik.com.benefit.bean.OnItemEventListener;
import kazeik.com.benefit.utils.AppUtils;
import kazeik.com.benefit.utils.HttpNetUtils;
import kazeik.com.benefit.utils.OnNetEventListener;
import kazeik.com.benefit.view.PopupWindowUtil;

public class MainActivity extends BaseActivity implements OnNetEventListener ,OnItemEventListener{

    @Bind(R.id.ll_botton_view)
    LinearLayout llBottonView;
    LayoutInflater inflater;
    int width;
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        inflater = LayoutInflater.from(this);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        getData();
    }

    private void getData() {
        String url = "getMenuList.php";
        HttpNetUtils.getInstance().requestNetData(HttpRequest.HttpMethod.GET, null, url, this);
    }

    @Override
    public void onNetSuccess(String tag, String body) {
        AppUtils.Logs(getClass(),body);
        List<MenuListModel> items = new Gson().fromJson(body, new TypeToken<List<MenuListModel>>() {
        }.getType());
        if(null != items){
            final int subWidth = width/items.size();
            for (final MenuListModel item :items) {
                RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.bottom_view,null);
                TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu_text);
                tvMenu.setText(item.item);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                view.setLayoutParams(params);
                llBottonView.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupWindowUtil util = new PopupWindowUtil();
                        util.dismissPopup();
                        util.showPopup(MainActivity.this,view,item,subWidth,MainActivity.this);
                    }
                });
            }
        }
    }

    @Override
    public void onNetError(String tag, String errorMsg, HttpException ex) {

    }

    @Override
    public void onItemEvent(String url) {
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
    }
}
