package kazeik.com.benefit.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import kazeik.com.benefit.R;
import kazeik.com.benefit.adapter.MenuAdapter;
import kazeik.com.benefit.bean.MenuListModel;
import kazeik.com.benefit.bean.OnItemEventListener;

/**
 * Created by Administrator on 2016/12/3.
 */

public class PopupWindowUtil {
    PopupWindow menuWindow;

    public void showPopup(Activity context, View attrView, final MenuListModel model, int viewWidth, final OnItemEventListener listener) {
        dismissPopup();
        View layout = LayoutInflater.from(context).inflate(R.layout.view_popup, null);
        final ListView lsView = (ListView) layout.findViewById(R.id.ls_listview);
        String[] item = new String[model.value.size()];
        for (int i = 0; i < item.length; i++) {
            item[i] = model.value.get(i).subMenuName;
        }
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, item);
//        MenuAdapter adapter = new MenuAdapter(context,model);
        lsView.setAdapter(adapter);
        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != listener) {
                    listener.onItemEvent(model.value.get(i).subMenuUrl);
                    dismissPopup();
                }
            }
        });
        menuWindow = new PopupWindow(layout,viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT); //后两个参数是width和height
        menuWindow.setFocusable(true);
        menuWindow.setOutsideTouchable(true);
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.update();
        menuWindow.showAsDropDown(attrView, 0, -195 * (item.length + 1));
    }

    public void dismissPopup() {
        if (null != menuWindow) {
            menuWindow.dismiss();
            menuWindow = null;
        }
    }
}
