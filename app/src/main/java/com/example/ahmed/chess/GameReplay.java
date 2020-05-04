package com.example.ahmed.chess;

import android.content.Intent;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameReplay extends Chess_Board {

    public static final String FROMX = "fromX";
    public static final String FROMY = "fromY";
    public static final String TOX = "toX";
    public static final String TOY = "toY";

    private Button next;
    private Button goHome;

    private TextView playerTurn;

    private int counter = 0;

    private List<Moves> movesToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_replay);

        next = (Button) findViewById(R.id.btnNext);
        goHome = (Button) findViewById(R.id.btnHome);

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
            }
        }

        playerTurn.setText(player());

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            movesToPlay = (List<Moves>) b.getSerializable(FROMX);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMove();
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }




    public void nextMove(){

        if (counter == movesToPlay.size()){
            Toast.makeText(getApplicationContext(), "Already At Last Move. Click \"Go Home\" to return " +
                            "to main screen",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (counter < movesToPlay.size()){
            Moves a = movesToPlay.get(counter);
            movePiece(a.getFromX(), a.getFromY(), a.getToX(), a.getToY());
            counter++;
            playerTurn.setText(player());
        }
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
}
