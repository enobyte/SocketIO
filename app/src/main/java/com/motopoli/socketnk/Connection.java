package com.motopoli.socketnk;

/**
 * Created by Eno on 5/11/2016.
 */
public abstract class Connection {
    public static final String url = "http://api.motopoli.com";
    abstract BrigdeConnection send(String channel, Object param);
    abstract String get(String channel);
    abstract void runConnect(Boolean connect);
}
