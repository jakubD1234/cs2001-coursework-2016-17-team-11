package com.rathor.hci.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rathor.hci.R;
import com.rathor.hci.adapter.NavDrawerItem;
import com.rathor.hci.adapter.NavDrawerListAdapter;
import com.rathor.hci.fragment.FragChat;
import com.rathor.hci.fragment.FragFriend;
import com.rathor.hci.fragment.FragIntersets;
import com.rathor.hci.fragment.FragEditProfile;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mtoolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ProgressDialog mBar;
    public ImageView mIvRight, mIvRightInside;
    public String mAllWords = "";
    public TextView mToolbarTitle;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBar = new ProgressDialog(MainActivity.this);
        mBar.setMessage(getResources().getString(R.string.Please_wait));
        mBar.setCancelable(false);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarOnActionBar();
        setListItems();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mtoolbar, 0, 0);
        mIvRight = (ImageView) findViewById(R.id.ivRight);
        mIvRightInside = (ImageView) findViewById(R.id.ivRightInside);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);


        mIvRightInside.setOnClickListener(this);
        mIvRight.setOnClickListener(this);

        RelativeLayout left_drawerLayout = (RelativeLayout) findViewById(R.id.relLayoutDrawer);
        float xdpi = getResources().getDisplayMetrics().widthPixels;
        int width = (int) (xdpi * 60 / 100);
        DrawerLayout.LayoutParams params2 = (DrawerLayout.LayoutParams) left_drawerLayout.getLayoutParams();
        params2.width = width;
        left_drawerLayout.setLayoutParams(params2);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString("type", "empty");
            displayViewOther(1, bundle);
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_drawer);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    private void setToolbarOnActionBar() {
        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListItems() {
        String[] mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray mNavMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[0], mNavMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[1], mNavMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[2], mNavMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[3], mNavMenuIcons.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[4], mNavMenuIcons.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[5], mNavMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(mNavMenuTitles[6], mNavMenuIcons.getResourceId(6, -1)));

        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {              //list of options given to the user
                if (position == 0) {
                    mToolbarTitle.setText("Interests");
                    displayViewOther(1, null);
                } else if (position == 1) {
                    mToolbarTitle.setText("Friends");
                    displayViewOther(2, null);
                } else if (position == 2) {
                    mToolbarTitle.setText("Chatting");
                    displayViewOther(3, null);
                } else if (position == 3) {
                    mToolbarTitle.setText("Edit Profile");
                    displayViewOther(4, null);
                } else if (position == 3) {

                } else if (position == 4) {
                    mToolbarTitle.setText("Rate App");
                    rateApp();
                } else if (position == 5) {
                    mToolbarTitle.setText("Share App");
                    shareApp("shareApp");
                } else if (position == 6) {
                    mToolbarTitle.setText("Logout");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                shareApp("shareWords");
                break;
            case R.id.ivRightInside:

                break;
        }
    }

    public void displayViewOther(int position, Bundle bundle) {
        Fragment fragment = null;
        switch (position) {

            case 1:
                fragment = new FragIntersets();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new FragFriend();
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = new FragChat();
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment = new FragEditProfile();
                fragment.setArguments(bundle);
                break;


            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }


    private void shareApp(String shareWords) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        if (shareWords.equals("shareWords"))
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mAllWords);
        else {
            String link_val = "http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName();
            String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
            String data = "Hello I am using English Dictionary App.<br>It has large numbers of Words meaning with synonyms.<br>Its works offline very smoothly.<br><br>" + body;
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(data));
        }

        startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
        }
    }


}
