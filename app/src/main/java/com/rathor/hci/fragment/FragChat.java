package com.rathor.hci.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rathor.hci.R;
import com.rathor.hci.activity.MainActivity;
import com.rathor.hci.adapter.MessageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragChat extends Fragment {
    private ListView listView;
    private View btnSend;
    private EditText inputBoxTv;
    private List<JSONObject> mListChat;
    private MessageAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chat, container, false);

        listView = (ListView) view.findViewById(R.id.list_msg);
        btnSend = view.findViewById(R.id.btn_chat_send);
        inputBoxTv = (EditText) view.findViewById(R.id.msg_type);

        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("name")) {
            activity.mToolbarTitle.setText(bundle.getString("name"));
        }

        mListChat = new ArrayList<>();

        try {
            JSONObject newObject = new JSONObject();
            JSONObject newObject2 = new JSONObject();
            JSONObject newObject3 = new JSONObject();
            newObject.put("message", "Hi..");
            newObject.put("time", "5 PM");
            newObject.put("type", "left");
            newObject2.put("message", "How are you ?");
            newObject2.put("time", "5.01 PM");
            newObject2.put("type", "left");
            newObject3.put("message", "What are you doing there");
            newObject3.put("time", "5.02 PM");
            newObject3.put("type", "left");

            mListChat.add(newObject);
            mListChat.add(newObject2);
            mListChat.add(newObject3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new MessageAdapter(getActivity(), mListChat,R.layout.item_chat_left);
        listView.setAdapter(mAdapter);
        scrollMyListViewToBottom();

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputBoxTv.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {

                    try {
                        JSONObject newObject = new JSONObject();
                        newObject.put("message", inputBoxTv.getText().toString());
                        newObject.put("time", getReminingTime());
                        newObject.put("type", "right");
                        mAdapter.mMessageList.add(mAdapter.mMessageList.size(), newObject);
                        mAdapter.updateList();
                        inputBoxTv.setText("");
                        scrollMyListViewToBottom();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        return view;
    }


    private String getReminingTime() {
        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(mAdapter.getCount() - 1);
            }
        });

    }

}