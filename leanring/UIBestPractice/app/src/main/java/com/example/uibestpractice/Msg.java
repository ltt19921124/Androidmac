package com.example.uibestpractice;

/**
 * Created by Tian Lu on 2017/6/8.
 */

public class Msg {

    public static final int TYPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;

    public String content;

    private int type;

    public Msg(String content,int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }


}
