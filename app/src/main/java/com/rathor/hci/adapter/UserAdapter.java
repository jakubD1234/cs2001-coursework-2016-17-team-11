package com.rathor.hci.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rathor.hci.R;
import com.rathor.hci.utils.ImageLoaderRound;

import org.json.JSONObject;

import java.util.List;


public class UserAdapter extends BaseAdapter {
    private Context mContaxt;
    private ImageLoaderRound mImageLoaderRound;
    public List<JSONObject> mDataList;


    public UserAdapter(Context context, List<JSONObject> list) {
        mContaxt = context;
        mDataList = list;
        mImageLoaderRound = ImageLoaderRound.getInstance(mContaxt);

    }

    @Override
    public int getCount() {
        // return mDataList.size();
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(mContaxt, R.layout.frag_friendlist_adapter, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
        if (position == 0) {
            textView1.setText("Binesh Kumar");
            mImageLoaderRound.DisplayImage("abc", imageView, 100, null, true);
        } else if (position == 1) {
            textView1.setText("Ramkesh Yadav");
            mImageLoaderRound.DisplayImage("abc", imageView, 100, null, true);
        } else if (position == 2) {
            textView1.setText("Manik Khana");
            mImageLoaderRound.DisplayImage("abc", imageView, 100, null, true);
        } else if (position == 3) {
            textView1.setText("Akash Singh");
            mImageLoaderRound.DisplayImage("abc", imageView, 100, null, true);
        } else if (position == 4) {
            textView1.setText("Kamal  Kumar");
            mImageLoaderRound.DisplayImage("abc", imageView, 100, null, true);
        }

        return convertView;
    }

    public void updateList(List<JSONObject> list, String type) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }


}
