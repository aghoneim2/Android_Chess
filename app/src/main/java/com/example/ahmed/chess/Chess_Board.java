package com.example.ahmed.chess;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import engine.Board;
import engine.Pieces;
import engine.Types;

public class Chess_Board extends AppCompatActivity implements GameDialog.GameDialogListener{

    public ImageHolder[][] iviews = new ImageHolder[8][8];
    private int fromX = 0;
    private int fromY = 0;
    private int toX = 0;
    private int toY = 0;
    private boolean isSelected = false;
    private boolean isWhiteTurn = true;
    public Board board = new Board();
    public Undo undo;
    private boolean didUndo = false;
    private boolean draw = false;

    private ArrayList<Moves> moves = new ArrayList<Moves>();

    /* On start - creates and sets up the board */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess__board);

        setIviews();

        drawPicture();

        for (int i = 0; i < iviews.length; i++) {
            for (int j = 0; j < iviews[i].length; j++) {
                if (((i + j) % 2) == 0) {
                    iviews[i][j].getImageView().setBackgroundColor(getResources().getColor(R.color.aqua)/*Color.GRAY*/);
                } else {
                    iviews[i][j].getImageView().setBackgroundColor(getResources().getColor(R.color.Blue)/*Color.WHITE*/);
                }

                //set up engine board
                board.fillBoard2();

                iviews[i][j].getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = v.getId();
                        onClickGrid(index);
                    }
                });
            }
        }
    }

    /* Interaction with pieces on the interactive board */
    public void onClickGrid(int index){
        /*
        This is the method to move pieces
         */

        String id = index + "";

        int i = Integer.parseInt(id.charAt(1) + "");
        int j = Integer.parseInt(id.charAt(2) + "");

        if (!isSelected){
            //piece was just selected
            isSelected = true;
            fromX = i;
            fromY = j;
            setBackGround(fromX, fromY, true);
        }else if (isSelected){
            //piece was previously selected, now must move
            isSelected = false;
            toX = i;
            toY = j;

            if(fromX == toX && fromY == toY){
                //clicked on the same cell, not valid move
                setBackGround(fromX, fromY, false);
                return;
            }

            movePiece(fromX, fromY, toX, toY);
            setBackGround(fromX, fromY, false);
        }
    }

    /* Moves selected pieces on the board  */
    public void movePiece(int fromX, int fromY, int toX, int toY){
        undo = new Undo(Board.board2[fromX][fromY], Board.board2[toX][toY]);
        if (didUndo) {
            moves.remove(moves.size()-1);
            setDidUndo(false);
        }
        if (Board.board2[fromX][fromY].getPiece().getColor() == 0 && (isWhiteTurn)) {
            int x = board.translateInput(fromX, fromY, toX, toY);
            if (x == 1){
                isWhiteTurn = false;
                moves.add(new Moves(fromX, fromY, toX, toY));
            }
            else
                return;
        }
        else if (Board.board2[fromX][fromY].getPiece().getColor() == 1 && (!isWhiteTurn)){
            int x = board.translateInput(fromX, fromY, toX, toY);
            if (x == 1){
                isWhiteTurn = true;
                moves.add(new Moves(fromX, fromY, toX, toY));
            }
            else
                return;
        }else{
            return;
        }

        if (board.isKingDead()){
            if (ChessReplay.chessReplay)
                return;

            openDialog(0);
            return;
        }

        drawImage(fromX, fromY, toX, toY);


        if (board.isCheckPublic() == false)
            Toast.makeText(getApplicationContext(), "King is in check!", Toast.LENGTH_SHORT).show();
    }

    /* Sets piece attributes on movement - updates board */
    public void drawImage(int fromX, int fromY, int toX, int toY){
        if (Board.board2[fromX][fromY].isFilled() == false){
            iviews[fromX][fromY].getImageView().setImageResource(R.drawable.transparent);
        }

        if (Board.board2[toX][toY].isFilled() == false){
            iviews[toX][toY].getImageView().setImageResource(R.drawable.transparent);
        }

        if (Board.board2[fromX][fromY].isFilled()){
            Pieces p = Board.board2[fromX][fromY].getPiece();
            switch (p.getType()){
                case ROOK:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whiterook);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackrook);
                    break;
                case KNIGHT:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whiteknight);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackknight);
                    break;
                case BISHOP:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whitebishop);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackbishop);
                    break;
                case QUEEN:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whitequeen);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackqueen);
                    break;
                case KING:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whiteking);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackking);
                    break;
                case PAWN:
                    if(p.getColor() == 0)
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.whitepawn);
                    else
                        iviews[fromX][fromY].getImageView().setImageResource(R.drawable.blackpawn);
                    break;
            }

        }

        if (Board.board2[toX][toY].isFilled()){
            Pieces p = Board.board2[toX][toY].getPiece();
            switch (p.getType()){
                case ROOK:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whiterook);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackrook);
                    break;
                case KNIGHT:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whiteknight);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackknight);
                    break;
                case BISHOP:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whitebishop);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackbishop);
                    break;
                case QUEEN:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whitequeen);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackqueen);
                    break;
                case KING:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whiteking);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackking);
                    break;
                case PAWN:
                    if(p.getColor() == 0)
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.whitepawn);
                    else
                        iviews[toX][toY].getImageView().setImageResource(R.drawable.blackpawn);
                    break;
            }

        }
    }

    /* sets board's background */
    public void setBackGround(int x, int y, boolean selected){
        if(selected){
            iviews[x][y].getImageView().setBackgroundColor(getResources().getColor(R.color.red));
        }else{
            if (((x + y) % 2) == 0) {
                iviews[x][y].getImageView().setBackgroundColor(getResources().getColor(R.color.aqua)/*Color.GRAY*/);
            } else {
                iviews[x][y].getImageView().setBackgroundColor(getResources().getColor(R.color.Blue)/*Color.WHITE*/);
            }
        }
    }

    /* sets attributes-images for the board */
    public void setIviews() {
        int id = 100;
        for (int i = 0; i < iviews.length; i++) {
            if (i == 0)
                id = 100;
            if (i == 1)
                id = 110;
            if (i == 2)
                id = 120;
            if (i == 3)
                id = 130;
            if (i == 4)
                id = 140;
            if (i == 5)
                id = 150;
            if (i == 6)
                id = 160;
            if (i == 7)
                id = 170;

            for (int j = 0; j < iviews[0].length; j++) {
                if (i == 0) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv00), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv01), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv02), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv03), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv04), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv05), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv06), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv07), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                } else if (i == 1) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv10), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv11), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv12), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv13), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv14), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv15), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv16), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv17), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                } if (i == 2) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv20), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv21), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv22), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv23), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv24), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv25), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv26), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv27), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                } else if (i == 3) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv30), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv31), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv32), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv33), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv34), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv35), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv36), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv37), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                }else if (i == 4) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv40), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv41), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv42), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv43), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv44), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv45), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv46), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv47), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                } else if (i == 5) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv50), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv51), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv52), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv53), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv54), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv55), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv56), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv57), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                }else if (i == 6) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv60), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv61), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv62), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv63), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv64), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv65), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv66), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv67), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                } else if (i == 7) {
                    switch (j) {
                        case 0:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv70), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 1:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv71), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 2:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv72), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 3:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv73), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 4:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv74), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 5:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv75), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 6:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv76), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                        case 7:
                            iviews[i][j] = new ImageHolder((ImageView) findViewById(R.id.iv77), i, j);
                            iviews[i][j].getImageView().setId(id);
                            id++;
                            break;
                    }
                }
            }
        }
    }

    /* sets interactive pieces on the interactive board */
    protected void drawPicture() {
        //
        iviews[0][0].getImageView().setImageResource(R.drawable.blackrook);
        iviews[0][1].getImageView().setImageResource(R.drawable.blackknight);
        iviews[0][2].getImageView().setImageResource(R.drawable.blackbishop);
        iviews[0][3].getImageView().setImageResource(R.drawable.blackqueen);
        iviews[0][4].getImageView().setImageResource(R.drawable.blackking);
        iviews[0][5].getImageView().setImageResource(R.drawable.blackbishop);
        iviews[0][6].getImageView().setImageResource(R.drawable.blackknight);
        iviews[0][7].getImageView().setImageResource(R.drawable.blackrook);
        //
        for (int i = 0; i < 8; i++){
            iviews[1][i].getImageView().setImageResource(R.drawable.blackpawn);
        }
        //
        for (int i = 0; i < 8; i++){
            iviews[2][i].getImageView().setImageResource(R.drawable.transparent);
        }
        //
        for (int i = 0; i < 8; i++){
            iviews[3][i].getImageView().setImageResource(R.drawable.transparent);
        }
        //
        for (int i = 0; i < 8; i++){
            iviews[4][i].getImageView().setImageResource(R.drawable.transparent);
        }
        //
        for (int i = 0; i < 8; i++){
            iviews[5][i].getImageView().setImageResource(R.drawable.transparent);
        }
        //
        for (int i = 0; i < 8; i++){
            iviews[6][i].getImageView().setImageResource(R.drawable.whitepawn);
        }
        //
        iviews[7][0].getImageView().setImageResource(R.drawable.whiterook);
        iviews[7][1].getImageView().setImageResource(R.drawable.whiteknight);
        iviews[7][2].getImageView().setImageResource(R.drawable.whitebishop);
        iviews[7][3].getImageView().setImageResource(R.drawable.whitequeen);
        iviews[7][4].getImageView().setImageResource(R.drawable.whiteking);
        iviews[7][5].getImageView().setImageResource(R.drawable.whitebishop);
        iviews[7][6].getImageView().setImageResource(R.drawable.whiteknight);
        iviews[7][7].getImageView().setImageResource(R.drawable.whiterook);
        //
    }

    /* return boolean for a white piece on the board */
    public boolean isWhite(){
        return isWhiteTurn;
    }

    /* sets boolean for a white piece on the board */
    public void setIsWhite(boolean x){
        isWhiteTurn = x;
    }

    /* Used for Undo button */
    public void setDidUndo(boolean undo){
        didUndo = undo;
    }

    /* Gets Undo info */
    public boolean getDidUndo(){
        return didUndo;
    }

    /* sets a boolean for board draw */
    public void setDraw(){
        draw = true;
    }

    /* Gets a boolean for board draw */
    public boolean getDraw(){
        return draw;
    }

    /*Gets list of moves to replay*/
    public List<Moves> getMoves(){
        return moves;
    }

    @Override
    public void applyTexts(String fileName) throws IOException {
        //take fileName and save the game

        ///first will save fileName in a list of fileNames
        //these fileNames will be displayed in the Replay Activity

        String FILE_NAME = fileName;
        FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(getMoves());
        fos.close();
        oos.close();
    }

    /* Displays interactive dialogs for notifications in-game */
    public void openDialog(int type){
        if (type == 0){
            //game winner, save request

            if (board.isKingDead()){
                if (board.getKingKiller() == 0){
                    //white winner
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(Chess_Board.this, android.R.style.Theme_Material_Dialog_Alert);
                    else {
                        builder = new AlertDialog.Builder(getApplicationContext());
                    }
                    builder.setTitle("Game Over")
                            .setMessage("White Wins! Do you Want to save this game?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    GameDialog gameDialog = new GameDialog();
                                    gameDialog.show(getSupportFragmentManager(), "display winner");
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    Intent intent = new Intent(Chess_Board.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    //black winner
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(Chess_Board.this, android.R.style.Theme_Material_Dialog_Alert);
                    else {
                        builder = new AlertDialog.Builder(getApplicationContext());
                    }
                    builder.setTitle("Game Over")
                            .setMessage("Black Wins! Do you Want to save this game?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                    GameDialog gameDialog = new GameDialog();
                                    gameDialog.show(getSupportFragmentManager(), "display winner");
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    Intent intent = new Intent(Chess_Board.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        }else if (type == 1){
            //draw
            //2nd Alert Dialog
            AlertDialog.Builder alertDialogBuilderSuccess = new AlertDialog.Builder(
                    Chess_Board.this);
            alertDialogBuilderSuccess.setTitle("Game Over");
            // set dialog message

            alertDialogBuilderSuccess
                    .setMessage(
                            "Game ended in draw! " + "Would you like to save this game?")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    GameDialog gameDialog = new GameDialog();
                                    gameDialog.show(getSupportFragmentManager(), "display winner");
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Chess_Board.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                    );

            // create alert dialog
            final AlertDialog alertDialogSuccess = alertDialogBuilderSuccess.create();

            //////////////////////////////////
            //1st Alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Chess_Board.this);
            alertDialogBuilder.setTitle("Draw Request");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Opponent has requested draw. Do you accept?")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {

                                    //calling the second alert when it user press the confirm button
                                    alertDialogSuccess.show();
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    if (isWhite()){
                                        setIsWhite(false);
                                    }else{
                                        setIsWhite(true);
                                    }
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }else if (type == 2){
            //resign

            String player = "";
            if (isWhite())
                player+="White Resigned! ";
            else
                player+= "Black Resigned! ";

            //2nd Alert Dialog
            AlertDialog.Builder alertDialogBuilderSuccess = new AlertDialog.Builder(
                    Chess_Board.this);
            alertDialogBuilderSuccess.setTitle("Game Over");
            // set dialog message
            alertDialogBuilderSuccess
                    .setMessage(
                            player + "Would you like to save this game?")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    GameDialog gameDialog = new GameDialog();
                                    gameDialog.show(getSupportFragmentManager(), "display winner");
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Chess_Board.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                    );

            // create alert dialog
            final AlertDialog alertDialogSuccess = alertDialogBuilderSuccess.create();

            //////////////////////////////////
            //1st Alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Chess_Board.this);
            alertDialogBuilder.setTitle("Resign");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to resign?")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {

                                    //calling the second alert when it user press the confirm button
                                    alertDialogSuccess.show();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    if (isWhite()){
                                        setIsWhite(false);
                                    }else{
                                        setIsWhite(true);
                                    }
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

}

