package kazeik.com.benefit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import kazeik.com.benefit.R;
import kazeik.com.benefit.bean.MenuListModel;

/**
 * Created by Administrator on 2016/12/3.
 */

public class MenuAdapter extends BaseAdapter {
    MenuListModel model;
    Context context;
    LayoutInflater inflater;

    public MenuAdapter(Context context, MenuListModel tem) {
        model = tem;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return model == null || model.value.isEmpty() ? 0 : model.value.size();
    }

    @Override
    public Object getItem(int i) {
        return model.value.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.bottom_view, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvMenuText.setText(model.value.get(i).subMenuName);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_menu_text)
        TextView tvMenuText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
