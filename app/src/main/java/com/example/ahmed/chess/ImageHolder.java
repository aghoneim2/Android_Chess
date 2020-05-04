package com.example.ahmed.chess;

import android.widget.ImageView;

public class ImageHolder {

    private ImageView imageView;
    private int x;
    private int y;

    /* Constructor -  Sets holder for piece image given the attributes */
    public ImageHolder(ImageView imageView, int x, int y){
        this.imageView = imageView;
        this.x = x;
        this.y = y;
    }

    /* Sets holder for piece image given the attributes */
    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    /* Gets image piece */
    public ImageView getImageView(){
        return imageView;
    }

    /* Sets X coordinate for image holder attribute */
    public void setX(int x){
        this.x = x;
    }

    /* Gets X coordinate for image holder attribute */
    public int getX(){
        return this.x;
    }

    /* Sets Y coordinate for image holder attribute */
    public void setY(int y){
        this.y = y;
    }

    /* Gets Y coordinate for image holder attribute */
    public int getY(){
        return this.y;
    }

}
