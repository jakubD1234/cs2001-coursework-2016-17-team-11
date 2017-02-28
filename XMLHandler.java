package com.example.zohaib.unibuddy;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

import com.example.zohaib.unibuddy.IUpdateData;
import com.example.zohaib.unibuddy.FriendInfo;
import com.example.zohaib.unibuddy.MessageInfo;
import com.example.zohaib.unibuddy.STATUS;


/**
 * Created by Bary on 22/02/2017.
 */

public class XMLHandler extends DefaultHandler {
    private String userKey = new String();
    private IUpdateData updater;

    public XMLHandler(IUpdateData updater) {
        super();
        this.updater = updater;
    }

    private Vector<FriendInfo> mFriends = new Vector<FriendInfo>();
    private Vector<FriendInfo> mOnlineFriends = new Vector<FriendInfo>();
    private Vector<FriendInfo> mUnapprovedFriends = new Vector<FriendInfo>();

    private Vector<MessageInfo> mUnreadMessages = new Vector<MessageInfo>();


    public void endDocument() throws SAXException {
        FriendInfo[] friends = new FriendInfo[mFriends.size() + mOnlineFriends.size()];
        MessageInfo[] messages = new MessageInfo[mUnreadMessages.size()];

        int onlineFriendCount = mOnlineFriends.size();
        for (int i = 0; i < onlineFriendCount; i++) {
            friends[i] = mOnlineFriends.get(i);
        }


        int offlineFriendCount = mFriends.size();
        for (int i = 0; i < offlineFriendCount; i++) {
            friends[i + onlineFriendCount] = mFriends.get(i);
        }

        int unApprovedFriendCount = mUnapprovedFriends.size();
        FriendInfo[] unApprovedFriends = new FriendInfo[unApprovedFriendCount];

        for (int i = 0; i < unApprovedFriends.length; i++) {
            unApprovedFriends[i] = mUnapprovedFriends.get(i);
        }

        int unreadMessagecount = mUnreadMessages.size();
        //Log.i("MessageLOG", "mUnreadMessages="+unreadMessagecount );
        for (int i = 0; i < unreadMessagecount; i++) {
            messages[i] = mUnreadMessages.get(i);
            Log.i("MessageLOG", "i=" + i);
        }

        this.updater.updateData(messages, friends, unApprovedFriends, userKey);
        super.endDocument();
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        if (localName == "friend") {
            FriendInfo friend = new FriendInfo();
            friend.userName = attributes.getValue(FriendInfo.USERNAME);
            String status = attributes.getValue(FriendInfo.STATUS);
            friend.ip = attributes.getValue(FriendInfo.IP);
            friend.port = attributes.getValue(FriendInfo.PORT);
            friend.userKey = attributes.getValue(FriendInfo.USER_KEY);
            //friend.expire = attributes.getValue("expire");

            if (status != null && status.equals("online")) {
                friend.status = STATUS.ONLINE;
                mOnlineFriends.add(friend);
            } else if (status.equals("unApproved")) {
                friend.status = STATUS.UNAPPROVED;
                mUnapprovedFriends.add(friend);
            } else {
                friend.status = STATUS.OFFLINE;
                mFriends.add(friend);
            }
        } else if (localName == "user") {
            this.userKey = attributes.getValue(FriendInfo.USER_KEY);
        } else if (localName == "message") {
            MessageInfo message = new MessageInfo();
            message.userid = attributes.getValue(MessageInfo.USERID);
            message.sendt = attributes.getValue(MessageInfo.SENDT);
            message.messagetext = attributes.getValue(MessageInfo.MESSAGETEXT);

            try {
                message.tgt = attributes.getValue(MessageInfo.TGT);
                message.picname = attributes.getValue(MessageInfo.PICName); //piclink should be complete storage name + name of image
                //System.out.println("Name is " + message.picname);

                String temp1 = attributes.getValue(MessageInfo.ENCRYPTEDIMAGE);

                message.encryptedimage = temp1.getBytes("UTF-8");
                //System.out.println(temp1);

              } catch (Exception e) {
                System.out.println("This was a text only message");
                message.picname = "";//empt
                message.encryptedimage = "".getBytes();//empty
            }

            Log.i("MessageLOG", message.userid + message.sendt + message.messagetext);
            mUnreadMessages.add(message);
            message = new MessageInfo();
        }
        super.startElement(uri, localName, name, attributes);
    }

    @Override
    public void startDocument() throws SAXException {
        this.mFriends.clear();
        this.mOnlineFriends.clear();
        this.mUnreadMessages.clear();
        super.startDocument();
    }

}