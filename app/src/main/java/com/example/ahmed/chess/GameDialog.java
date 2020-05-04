package com.example.ahmed.chess;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GameDialog extends AppCompatDialogFragment {

    private EditText editFileName;
    private GameDialogListener listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (GameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement GameDialogListener" );
        }
    }

    /* Dialog notification for a game save or cancel */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Save Game")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switchToMain();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileName = editFileName.getText().toString();
                        try {
                            listener.applyTexts(fileName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), "File saved as \" " + fileName + " \" ", Toast.LENGTH_SHORT).show();
                        switchToMain();
                    }
                });

        editFileName = (EditText) view.findViewById(R.id.textView_fileName);
        return builder.create();
    }

    /* Switches to main menu */
    public void switchToMain(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    /* Listener method for dialog notifications */
    public interface GameDialogListener{
        void applyTexts(String fileName) throws IOException;
    }
}