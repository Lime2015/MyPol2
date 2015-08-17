package com.lime.mypol.models;

/**
 * Created by seongsan on 2015-08-17.
 */
public class Menu {
    private String title;
    private String info;
    private int color;

    public Menu(){}
    public Menu(String title, String info, int color) {
        this.title = title;
        this.info = info;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
