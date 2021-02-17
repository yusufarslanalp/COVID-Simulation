package com.company;

public class Map {
    int width;
    int height;


    public Map(int width, int height) {
        this.width = width;
        this.height = height;
    }

    boolean valid_cordinate( Cordinate cordinate )
    {
        boolean valid_x = (cordinate.x >= 0) & (cordinate.x <= width);
        boolean valid_y = (cordinate.y >= 0) & (cordinate.y <= height);
        if(  valid_x & valid_y) return true;
        else return false;
    }

}
