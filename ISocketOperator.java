package com.example.zohaib.unibuddy;

/**
 * Created by Bary on 21/02/2017.
 */

public interface ISocketOperator {
    public String sendHttpRequest(String params);
    public int startListening(int port);
    public void stopListening();
    public void exit();
    public int getListeningPort();
}
