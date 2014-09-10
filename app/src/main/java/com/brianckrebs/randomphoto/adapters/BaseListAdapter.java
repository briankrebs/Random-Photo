package com.brianckrebs.randomphoto.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by BrianK on 9/9/2014.
 */
public abstract class BaseListAdapter extends BaseAdapter {

    protected Context context;
    protected ArrayList items;

    public BaseListAdapter(Context context, ArrayList items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);
}
