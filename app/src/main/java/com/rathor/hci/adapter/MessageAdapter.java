package com.rathor.hci.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rathor.hci.R;

import org.json.JSONObject;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<JSONObject> {
    public List<JSONObject> mMessageList;
    private Activity activity;

    public MessageAdapter(Activity context, List<JSONObject> objects,int resource) {
        super(context, resource, objects);
        this.activity = context;
        this.mMessageList = objects;
    }
    private class ViewHolder {
        private TextView msg;
        private TextView timeStamp;
        private LinearLayout bubbleLl;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            timeStamp = (TextView) v.findViewById(R.id.timeStamp);
            bubbleLl = (LinearLayout) v.findViewById(R.id.bubbleLl);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {

            int layoutResource = R.layout.item_chat_right; // TODO remove file item_chat_left
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        JSONObject chatMessage = mMessageList.get(position);
        holder.msg.setText(chatMessage.optString("message"));
        holder.timeStamp.setText(chatMessage.optString("time"));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.bubbleLl.getLayoutParams();
        if (chatMessage.optString("type").equals("left")) {
            holder.bubbleLl.setBackgroundResource(R.drawable.bubble_in);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            holder.msg.setTextColor(Color.BLACK);
        }else {
            holder.bubbleLl.setBackgroundResource(R.drawable.bubble_out);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            holder.msg.setTextColor(Color.WHITE);
        }
        holder.bubbleLl.setLayoutParams(params);

        return convertView;
    }


    public void updateList() {
       // mMessageList.clear();
       // mMessageList.addAll(list);
        notifyDataSetChanged();
    }
}
