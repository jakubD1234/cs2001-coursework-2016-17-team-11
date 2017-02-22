package com.rathor.hci.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rathor.hci.R;

import java.util.ArrayList;



public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<NavDrawerItem> navDrawerItems;
    public int mSelectedPosition;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        mSelectedPosition = -1;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title1);
        ImageView image = (ImageView) convertView.findViewById(R.id.icon);

        txtTitle.setText(navDrawerItems.get(position).getTitle());
        image.setImageResource(navDrawerItems.get(position).getIcon());

        return convertView;
    }


}
