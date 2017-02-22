package com.rathor.hci.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSession {

    private SharedPreferences prefs;

    private static AppSession session;

    public static AppSession getInstance(Context cntx) {
        if (session == null)
            session = new AppSession(cntx);
        return session;
    }

    public AppSession(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserID(String UserID) {
        prefs.edit().putString("UserID", UserID).commit();
    }

    public String getUserID() {
        String UserID = prefs.getString("UserID", "");
        return UserID;
    }



    public void setPhoneNumber(String phoneNumber) {
        prefs.edit().putString("phoneNumber", phoneNumber).commit();
    }

    public String getPhoneNumber() {
        return prefs.getString("phoneNumber", "");
    }

    public String getLanguage() {
        return prefs.getString("language", "");
    }

    public void setLanguage(String language) {
        prefs.edit().putString("language", language).commit();
    }


    public void setUserDescription(String Description) {
        prefs.edit().putString("Description", Description).commit();
    }

    public String getUserDescription() {
        String Description = prefs.getString("Description", "");
        return Description;
    }

    public void setEmail(String Email) {
        prefs.edit().putString("Email", Email).commit();
    }

    public String getEmail() {
        return prefs.getString("Email", "");
    }


    public void setUserName(String Username) {
        prefs.edit().putString("Username", Username).commit();
    }

    public String getUserName() {
        return prefs.getString("Username", "");
    }

    public void setImageUrl(String ImageUrl) {
        prefs.edit().putString("ImageUrl", ImageUrl).commit();
    }

    public String getImageUrl() {
        String ImageUrl = prefs.getString("ImageUrl", "");
        return ImageUrl;
    }

    public void setUserPortfolioCount(int count) {
        prefs.edit().putInt("count", count).commit();
    }

    public Integer getUserPortfolioCount() {
        return prefs.getInt("count", 0);
    }




    public void setDiaplayName(String name) {
        prefs.edit().putString("name", name).commit();
    }

    public String getDisplayName() {
        String name = prefs.getString("name", "");
        return name;
    }

    public void setDiscussionNotification(boolean DiscussionNotification) {
        prefs.edit().putBoolean("DiscussionNotification", DiscussionNotification).commit();
    }

    public boolean getDiscussionNotification() {
        return prefs.getBoolean("DiscussionNotification", false);
    }

    public void setFriendNotification(boolean FriendNotification) {
        prefs.edit().putBoolean("FriendNotification", FriendNotification).commit();
    }

    public boolean getFriendNotification() {
        boolean FriendNotification = prefs.getBoolean("FriendNotification", false);
        return FriendNotification;
    }

    public void setNewsNotification(boolean NewsNotification) {
        prefs.edit().putBoolean("NewsNotification", NewsNotification).commit();
    }

    public boolean getNewsNotification() {
        return prefs.getBoolean("NewsNotification", false);
    }


    public void setUserType(String Usertype) {
        prefs.edit().putString("Usertype", Usertype).commit();
    }

    public String getUserType() {
        String Usertype = prefs.getString("Usertype", "");
        return Usertype;
    }


    public void setHasLoging(boolean value) {
        prefs.edit().putBoolean("hasLoging", value).commit();
    }

    public boolean getHasLoging() {
        return prefs.getBoolean("hasLoging", false);
    }

    public void setDeviceToken(String value) {
        prefs.edit().putString("deviceToken", value).commit();
    }

    public String getDeviceToken() {
        String deviceToken = prefs.getString("deviceToken", "");
        return deviceToken;
    }

    public void clear() {
        prefs.edit().clear().commit();
    }


}