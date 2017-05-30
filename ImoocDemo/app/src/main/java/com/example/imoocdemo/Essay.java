package com.example.imoocdemo;

/**
 * Created by Tian Lu on 2017/5/29.
 */

public class Essay {
    private String title;
    private String author;
    private String content;

    public Essay(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
