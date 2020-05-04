package com.example.ahmed.chess;

import java.io.Serializable;

/*
Holds movement of each piece of regular game to save later on
 */
public class Moves implements Serializable {

    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    /* Constructor to instantiate each movement, from and to*/
    public Moves(int fromX, int fromY, int toX, int toY){
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    /* Gets Origin X */
    public int getFromX() {
        return fromX;
    }

    /* Gets Origin Y */
    public int getFromY() {
        return fromY;
    }

    /*Gets Destination X */
    public int getToX() {
        return toX;
    }

    /* Gets Destination Y */
    public int getToY() {
        return toY;
    }
}
