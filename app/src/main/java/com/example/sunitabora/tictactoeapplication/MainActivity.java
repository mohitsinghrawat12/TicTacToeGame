package com.example.sunitabora.tictactoeapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3, restartButton;
    Button[] buttonsArray;
    boolean turn = true;
    int turn_count = 0;
    String playerName = "";
    int restartButtonCount = 0;
    int playersXTotal = 0;
    int playersYTotal = 0;
    public TextView playerXName, playerYName, playerXCount, playerYCount;
    TextView[] textViewArray;
    String[] colorArray = {"#6814ac", "#32cd32", "#008080", "#ffd700", "#FF00FF", "#754C78", "#008000", "#4876FF", "#AA6600", "#00611C", "#00E5EE", "#FF0000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);
        playerXName = (TextView) findViewById(R.id.textViewNameX);
        playerYName = (TextView) findViewById(R.id.textViewName0);
        playerXCount = (TextView) findViewById(R.id.textViewCountX);
        playerYCount = (TextView) findViewById(R.id.textViewCount0);
        restartButton = (Button) findViewById(R.id.buttonRestart);
        buttonsArray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};
        textViewArray = new TextView[]{playerXName, playerYName, playerXCount, playerYCount};
        for (Button b : buttonsArray) {
            b.setOnClickListener(this);
        }
        playerXName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertPopup(v);
            }
        });
        playerYName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertPopup(v);
            }

        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartButtonCount++;
                for (Button b : buttonsArray) {

                    Animation animation1 =
                            AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.fade1);
                    b.startAnimation(animation1);


                    turn = true;
                    turn_count = 0;
                    b.setText("");
                    b.setClickable(true);
                    if (restartButtonCount != colorArray.length) {
                        b.setBackgroundColor(Color.parseColor(colorArray[restartButtonCount]));
                        restartButton.setBackgroundColor(Color.parseColor(colorArray[restartButtonCount]));
                    } else {
                        restartButtonCount = 0;
                        b.setBackgroundColor(Color.parseColor(colorArray[restartButtonCount]));
                        restartButton.setBackgroundColor(Color.parseColor(colorArray[restartButtonCount]));
                    }
                }
                for (TextView textView : textViewArray) {
                    if (restartButtonCount != colorArray.length) {
                        textView.setTextColor(Color.parseColor(colorArray[restartButtonCount]));
                    } else {
                        restartButtonCount = 0;
                        textView.setTextColor(Color.parseColor(colorArray[restartButtonCount]));
                    }
                }
            }
        });
    }

    public void showAlertPopup(View v) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.alertprompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextUserName);

        final TextView textChanged = (TextView) v;
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                textChanged.setText(userInput.getText().toString());
//                                playerName = userInput.getText().toString();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade);
        b.startAnimation(animation1);
        buttonClicked(b);
    }

    private void buttonClicked(Button b) {

        //Turn one is always X is true
        if (turn) {
            b.setText("X");
        } else {
            b.setText("0");
        }

        //increment the turn  when till the total no moves(boxes)  if turn_count reaches to 9 it means no one is winner
        turn_count++;
        b.setBackgroundColor(Color.parseColor("#FF642B"));

        //making  button with "X" or "0" marked non clickable so that user cant click the button again and again
        b.setClickable(false);

        // Reverse the turn for B (On every consecutive click )
        turn = !turn;


        //Each time a click is made we check the winner
        checkForWinner();
    }

    private void checkForWinner() {
        boolean there_is_a_winner = false;

        // we added a new condition (!a1.isClickable()) so that no one is declared winner for the first time because for the very first time
        // all columns and rows(Buttons) value will be same(i.e blank) and below conditions will be true

        //Check for Horizontals
        if (a1.getText() == a2.getText() && a2.getText() == a3.getText() && !a1.isClickable()) {
            there_is_a_winner = true;
        } else if (b1.getText() == b2.getText() && b2.getText() == b3.getText() && !b1.isClickable()) {
            there_is_a_winner = true;
        } else if (c1.getText() == c2.getText() && c2.getText() == c3.getText() && !c1.isClickable()) {
            there_is_a_winner = true;
        }

        //Verticals
        if (a1.getText() == b1.getText() && b1.getText() == c1.getText() && !a1.isClickable()) {
            there_is_a_winner = true;
        } else if (a2.getText() == b2.getText() && b2.getText() == c2.getText() && !b2.isClickable()) {
            there_is_a_winner = true;
        } else if (a3.getText() == b3.getText() && b3.getText() == c3.getText() && !c3.isClickable()) {
            there_is_a_winner = true;
        }

        //Diagnols
        if (a1.getText() == b2.getText() && b2.getText() == c3.getText() && !a1.isClickable()) {
            there_is_a_winner = true;
        } else if (a3.getText() == b2.getText() && b2.getText() == c1.getText() && !b2.isClickable()) {
            there_is_a_winner = true;
        }

        if (there_is_a_winner) {
            //  0 has just played the game (turn is reversed)
            if (!turn) {
                playersXTotal = playersXTotal + 1;
                playerXCount.setText(String.valueOf(playersXTotal));
                Toast.makeText(getApplicationContext(), playerXName.getText() + " is Winner", Toast.LENGTH_LONG).show();

            } else {
                playersYTotal++;
                playerYCount.setText(String.valueOf(playersYTotal));
                Toast.makeText(getApplicationContext(), playerYName.getText() + " is Winner", Toast.LENGTH_LONG).show();
            }

            //After winning all remaings blocks (buttons) will get dissabled
            disableAllButtons(false);
        } else if (turn_count == 9) {
            // If the count reaches to 9 it means that no one is winner in the game
            Toast.makeText(getApplicationContext(), "MATCH DRAW", Toast.LENGTH_LONG).show();
            disableAllButtons(true);
        }

    }

    private void disableAllButtons(boolean disable) {
        for (Button b : buttonsArray) {
            b.setClickable(disable);
            b.setBackgroundColor(Color.parseColor("#c6c6c6"));
        }
    }


}
