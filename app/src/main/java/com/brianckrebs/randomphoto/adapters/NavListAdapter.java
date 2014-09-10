package com.brianckrebs.randomphoto.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianckrebs.randomphoto.R;
import com.brianckrebs.randomphoto.model.PhotoGenre;

import java.util.ArrayList;

/**
 * Created by BrianK on 9/9/2014.
 */
public class NavListAdapter extends BaseListAdapter {

    public NavListAdapter(Context context, ArrayList items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewholder;

        PhotoGenre item = (PhotoGenre) items.get(position);

        if(row==null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.nav_list_item, parent, false);
            viewholder.title = (TextView) row.findViewById(R.id.nav_list_item_title);
            row.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) row.getTag();
        }

        //set title
        viewholder.title.setText(item.getUiCopyResId());

        return row;
    }

    static class ViewHolder {
        public TextView title;
    }
}
