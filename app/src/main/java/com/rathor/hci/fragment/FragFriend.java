package com.rathor.hci.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.rathor.hci.R;
import com.rathor.hci.activity.MainActivity;
import com.rathor.hci.adapter.UserAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class FragFriend extends Fragment {
    private UserAdapter mAdapter1;
    private FloatingActionMenu mFloatingActionMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.freind_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        mAdapter1 = new UserAdapter(getActivity(), new ArrayList<JSONObject>());
        listView.setAdapter(mAdapter1);

        mFloatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.FloatingActionMenu1);
        mFloatingActionMenu.setIconAnimated(false);

        mFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionMenu.close(true);
                Bundle bundle = new Bundle();
                bundle.putString("type", "new");
                Toast.makeText(getActivity(), "Work is in Progress", Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity activity = (MainActivity) getActivity ();
                Bundle bundle = new Bundle();
                if (position == 0) {
                    bundle.putString("name", "Bines Kumar");
                } else if (position == 1) {
                    bundle.putString("name", "Ramkesh Yadav");
                } else if (position == 2) {
                    bundle.putString("name", "Manik Khana");
                } else if (position == 3) {
                    bundle.putString("name", "Akash Singh");
                } else if (position == 4) {
                    bundle.putString("name", "Kamal  Kumar");
                }

                activity.displayViewOther(3,bundle);
            }
        });
        return view;
    }


}
