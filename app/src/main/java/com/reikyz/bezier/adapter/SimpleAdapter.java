package com.reikyz.bezier.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reikyz.bezier.R;

import java.util.List;

/**
 * Created by reikyZ on 2016/10/15.
 */

public class SimpleAdapter extends BaseListAdapter<String> {

    public SimpleAdapter(Context context, List<String> models) {
        super(context, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.getViewHolder(context,
                convertView,
                parent,
                R.layout.simple_data,
                position);
        TextView tv = holder.getView(R.id.tv);
        tv.setText(models.get(position));
        return holder.getConvertView();
    }
}
