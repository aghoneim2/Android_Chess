package com.example.ahmed.chess;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import engine.Board;
import engine.Pieces;
import engine.Space;

public class ChessGame extends Chess_Board  {

    private Button ai;
    private Button undoButton;
    private Button draw;
    private Button resign;

    private TextView playerTurn;

    /* On start - start of the app - Player options display attributes */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_game_activity);

        ai = (Button) findViewById(R.id.btnAI);
        undoButton = (Button) findViewById(R.id.btnUndo);
        draw = (Button) findViewById(R.id.btnDraw);
        resign = (Button) findViewById(R.id.btnResign);

        playerTurn = (TextView) findViewById(R.id.message);

        playerTurn.setTextColor(Color.BLACK);

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
                        onClickGridSub(index);
                        if (undoButton.isClickable() == false)
                            undoButton.setClickable(true);
                    }
                });
            }
        }

        playerTurn.setText(player());

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDidUndo()){
                    Toast.makeText(getApplicationContext(), "Already used undo feature", Toast.LENGTH_SHORT);
                }else{
                    undoMove();
                    setDidUndo(true);
                    playerTurn.setText(player());
                }
            }
        });

        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiButton();
                playerTurn.setText(player());
            }
        });

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(1);
            }
        });

        resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(2);
            }
        });
    }

    /* Selection of piece on board */
    public void onClickGridSub(int index){
        onClickGrid(index);
        playerTurn.setText(player());
    }

    //sets the player turn
    public String player(){
        String player = "Player: ";
        if (isWhite()){
            player+= "White";
        }else{
            player+= "Black";
        }
        return player;
    }

    /* Undo option for the move */
    public void undoMove(){
        if (undo.isSet()){
            setBoardUndo(undo.getSpace1(), undo.getSpace2());
        }
    }

    /* Updates the board after the undo option */
    public void setBoardUndo(Space s1, Space s2){
        Board.board2[s1.getX()][s1.getY()] = s2;
        Board.board2[s2.getX()][s2.getY()] = s1;
        int x1 = s1.getX();
        int x2 = s2.getX();
        int y1 = s1.getY();
        int y2 = s2.getY();

        Board.board2[x1][y1].setX(x1);
        Board.board2[x1][y1].setY(y1);
        Board.board2[x1][y1].getPiece().setX(x1);
        Board.board2[x1][y1].getPiece().setY(y1);

        Board.board2[x2][y2].setX(x2);
        Board.board2[x2][y2].setY(y2);
        Board.board2[x2][y2].getPiece().setX(x2);
        Board.board2[x2][y2].getPiece().setY(y2);

        drawImage(Board.board2[x1][y1].getX(), Board.board2[x1][y1].getY(), Board.board2[x2][y2].getX(), Board.board2[x2][y2].getY());
        undo.flushUndo();

        if (isWhite())
            setIsWhite(false);
        else
            setIsWhite(true);

        playerTurn.setText(player());

    }

    /* Turn taken through AI playset */
    public void aiButton(){
        List<Pieces> piecesInPlay;
        int color = 0;
        if (isWhite()){
            //white turn
            color = 0;
            piecesInPlay = board.getWhiteInPlay();
        }else{
            //black turn
            color = 1;
            piecesInPlay = board.getBlackInPlay();
        }

        for (Pieces x : piecesInPlay){
            List<Integer> moves = board.getAIMoves(x);

            for (int i = 0; i < moves.size(); i+=2){

                movePiece(x.getX(), x.getY(), moves.get(i), moves.get(i+1));

                if (color == 0 && isWhite()){
                    //player did not change (white), move is invalid
                    continue;
                }else if (color == 1 && !isWhite()){
                    //player did not change (black), move is invalid
                    continue;
                }else{
                    //move is valid, no more checking
                    //have to set new x and y for pieces in array
                    x.setX(moves.get(i));
                    x.setY(moves.get(i+1));
                    return;
                }
            }
        }
    }



}
