package com.example.asus.headlinestoday.bean;

/**
 * Created by asus on 2017/9/6.
 */

public class LeftMenu {
    private String name;
    private int image;

    public LeftMenu(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
