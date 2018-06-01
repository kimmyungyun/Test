package com.example.user.myapplication;

import android.graphics.drawable.Drawable;

public class ImageItem {
    private Drawable image;
    public ImageItem(){

    }
    public void setIcon(Drawable Image){
        image = Image;
    }
    public Drawable getIcon(){
        return image;
    }
}
