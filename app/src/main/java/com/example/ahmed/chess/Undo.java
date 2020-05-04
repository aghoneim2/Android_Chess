package com.example.ahmed.chess;

import engine.Pieces;
import engine.Space;
import engine.Types;

public class Undo {

    private Space s1;
    private Space s2;

    private Pieces p1;
    private Pieces p2;

    /* Constructor - attributes for the undo move */
    public Undo(Space s1, Space s2){
        this.s1 = s1;
        this.s2 = s2;
        setToUndo();
    }

    /* Return piece of Player */
    public Pieces getP1() {
        return p1;
    }

    /* Sets piece for Player */
    public void setP1(Pieces p1) {
        this.p1 = p1;
    }

    /* Return piece of Player */
    public Pieces getP2() {
        return p2;
    }

    /* Sets piece for Player */
    public void setP2(Pieces p2) {
        this.p2 = p2;
    }

    /* Return space on the board for the undo option for player */
    public Space getSpace1(){
        return this.s1;
    }

    /* Return space on the board for the undo option for player */
    public Space getSpace2(){
        return this.s2;
    }

    /* Sets undo move */
    public void setToUndo(){
        if (s1.isFilled()){
            setP1(s1.getPiece());
        }else{
            s1 = new Space(s1.getX(), s1.getY(), new Pieces(Types.EMPTY, -1), false);
            setP1(s1.getPiece());
        }

        if (s2.isFilled()){
            setP2(s2.getPiece());
        }else{
            s2 = new Space(s1.getX(), s2.getY(), new Pieces(Types.EMPTY, -1), false);
            setP2(s2.getPiece());
        }
    }

    /* checks undo move */
    public boolean isSet(){
        if (s1 == null || s2 == null){
            return false;
        }
        return true;
    }

    public void flushUndo(){
        s1 = null;
        s2 = null;
        setP2(null);
        setP1(null);
    }
}
