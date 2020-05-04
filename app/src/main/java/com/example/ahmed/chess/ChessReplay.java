package com.example.ahmed.chess;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import engine.Board;
import engine.Pieces;
import engine.Types;

import static java.util.Locale.US;

public class ChessReplay extends AppCompatActivity {

    private ListView gameList;
    private ArrayList<Moves> theMoves;
    public static boolean chessReplay = false;

    /* On replay - sets up the board */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_replay);

        gameList = (ListView) findViewById(R.id.gamesList);

        try {
            populateListView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        registerClick();

    }

    /*Populates List of games saved*/
    private void populateListView() throws ParseException {
        //create list of items
        //find all the files inside files
        File file = getFilesDir();
        File[] fileList = file.listFiles();
        String[] gameName = new String[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
            gameName[i] = fileList[i].getName();
        }
        //build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.game, gameName);
        gameList.setAdapter(adapter);
    }

    /*Opens game after click on list*/
    private void registerClick()
    {
        gameList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        gameList.setSelector(android.R.color.darker_gray);
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView text2 = (TextView)viewClicked;
                String fileName = text2.getText().toString();

                try{
                    FileInputStream fis = getApplicationContext().openFileInput(fileName);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    theMoves = (ArrayList<Moves>) is.readObject();
                    is.close();
                    fis.close();

                    Bundle b = new Bundle();
                    b.putSerializable(GameReplay.FROMX, theMoves);
                    Intent intent = new Intent(getApplicationContext(), GameReplay.class);
                    intent.putExtras(b);
                    chessReplay = true;
                    startActivity(intent);
                }catch(IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /*gets moves*/
    public List<Moves> getTheMoves(){
        return theMoves;
    }
}