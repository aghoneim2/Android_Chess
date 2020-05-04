package com.example.ahmed.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button newGame;
    private Button listGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame = (Button) findViewById(R.id.new_game);
        listGames = (Button) findViewById(R.id.view_game);

        newGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                playGame();
            }
        });


        listGames.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                replayGame();
            }
        });
    }

    /* Starting a game */
    private void playGame(){
        Intent intent =  new Intent(this, ChessGame.class);
        startActivity(intent);
    }

    /* Starting a  replay option in game */
    private void replayGame(){
        Intent intent =  new Intent(this, ChessReplay.class);
        startActivity(intent);
    }
}
