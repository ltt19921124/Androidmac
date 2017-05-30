package com.example.imoocdemo;

/**
 * Created by Tian Lu on 2017/5/29.
 */

public class Outline {
    private int id;
    private String name;
    private String picSmall;
    private String picBig;
    private String description;
    private String learner;

    public Outline(int id, String name, String picSmall, String picBig, String description, String learner) {
        this.id = id;
        this.name = name;
        this.picSmall = picSmall;
        this.picBig = picBig;
        this.description = description;
        this.learner = learner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicSmall() {
        return picSmall;
    }

    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall;
    }

    public String getPicBig() {
        return picBig;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLearner() {
        return learner;
    }

    public void setLearner(String learner) {
        this.learner = learner;
    }
}
