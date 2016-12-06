package kazeik.com.benefit.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
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
import kazeik.com.benefit.utils.AppUtils;

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
        menuWindow = new PopupWindow(layout, viewWidth, LinearLayout.LayoutParams.WRAP_CONTENT); //后两个参数是width和height
        menuWindow.setFocusable(true);
        menuWindow.setOutsideTouchable(true);
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.update();
        menuWindow.showAsDropDown(attrView, 0, item.length + 1);// -195 * (item.length + 1)
    }

    public void dismissPopup() {
        if (null != menuWindow) {
            menuWindow.dismiss();
            menuWindow = null;
        }
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    /**
     * 获取屏幕高度(px)
     */
    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
