package com.rathor.hci.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.rathor.hci.R;
import com.rathor.hci.adapter.CustomGridAdapter;

public class FragIntersets extends Fragment {

    GridView grid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_intersets, null);
        CustomGridAdapter adapter = new CustomGridAdapter(getActivity(), web, imageId);
        grid = (GridView) view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    String[] web = {
            "Spiritual",
            "Gaming etc",
            "Fitness",
            "Politics",
            "Entrepreneurs",
            "Music",
            "Photography",
            "Places",
            "Games",
            "Technology",
            "Entertainment",
            "Fashion",
            "Brands",
            "Arts",
            "Travelling",
            "Sports"

    };
    int[] imageId = {
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,
            R.mipmap.info,


    };
}
