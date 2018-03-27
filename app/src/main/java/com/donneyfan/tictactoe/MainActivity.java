package com.donneyfan.tictactoe;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Matrix to keep track of current state
    private Grid grid;
    // Track the current turn
    private String turn;

    private Random rand;
    private TicTacToeAI ai;
    private boolean winState;
    private LinkedList<LinkedList<ImageButton>> imageButtons;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = new Grid();

        // Construct nested array
        imageButtons = new LinkedList<>();
        for (int i = 0; i < 3; i++){
            imageButtons.add(new LinkedList<ImageButton>());
        }

        // Populate with image buttons
        imageButtons.get(0).add((ImageButton) findViewById(R.id.grid0_0));
        imageButtons.get(0).add((ImageButton) findViewById(R.id.grid0_1));
        imageButtons.get(0).add((ImageButton) findViewById(R.id.grid0_2));
        imageButtons.get(1).add((ImageButton) findViewById(R.id.grid1_0));
        imageButtons.get(1).add((ImageButton) findViewById(R.id.grid1_1));
        imageButtons.get(1).add((ImageButton) findViewById(R.id.grid1_2));
        imageButtons.get(2).add((ImageButton) findViewById(R.id.grid2_0));
        imageButtons.get(2).add((ImageButton) findViewById(R.id.grid2_1));
        imageButtons.get(2).add((ImageButton) findViewById(R.id.grid2_2));

        // X starts first.
        turn = Grid.X;

        message = findViewById(R.id.scoreText);
    }

    public void changeGrid(View view) {
        String id = view.getTag().toString();
        int x = Integer.valueOf(id.substring(0, 1));
        int y = Integer.valueOf(id.substring(2, 3));

        // Do not allow overwrites
        if (!grid.getCoord(x, y).isEmpty()) return;

        try {
            if (turn.equals(Grid.X) && !winState) {
                InputStream stream = getAssets().open("X.png");
                Drawable d = Drawable.createFromStream(stream, null);
                imageButtons.get(x).get(y).setImageDrawable(d);
                grid.setX(x, y);
            } else if (turn.equals(Grid.O) && !winState) {
                InputStream stream = getAssets().open("O.png");
                Drawable d = Drawable.createFromStream(stream, null);
                imageButtons.get(x).get(y).setImageDrawable(d);
                grid.setO(x, y);
            }

            // Check for win condition
            if (Grid.checkWin(grid, turn)) {
                if (turn.equals(Grid.O)) message.setText("O wins!");
                else message.setText("X wins!");
                winState = true;
                return;
            }

            // If the AI is initialized, play its move
            if (ai != null) {
                int[] aiMove = ai.playMove();
                x = aiMove[0];
                y = aiMove[1];

                if (ai.player.equals(Grid.X) && !winState) {
                    InputStream stream = getAssets().open("X.png");
                    Drawable d = Drawable.createFromStream(stream, null);
                    imageButtons.get(x).get(y).setImageDrawable(d);
                    grid.setX(x, y);
                } else if (ai.player.equals(Grid.O) && !winState) {
                    InputStream stream = getAssets().open("O.png");
                    Drawable d = Drawable.createFromStream(stream, null);
                    imageButtons.get(x).get(y).setImageDrawable(d);
                    grid.setO(x, y);
                }

                // Check for win condition
                if (Grid.checkWin(grid, ai.player)) {
                    message.setText(ai.player + " wins!");
                    winState = true;
                }
            } else {
                // Change turns if AI is not playing
                if (turn.equals(Grid.X)) turn = Grid.O;
                else turn = Grid.X;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newGame(View view){
        // Clear the board and reset the win state
        grid.clear();
        winState = false;
        ai = null;

        // Clear the images
        for (LinkedList<ImageButton> currRow : imageButtons) {
            for (ImageButton button : currRow) {
                button.setImageResource(0);
            }
        }

        message.setText("X to start.");
    }

    /*
     * Sets up the aiGame.
     */
    public void aiGame(View view) {
        newGame(null);
        if (rand == null) rand = new Random();
        // Retrieve a new random num
        int randomNum = rand.nextInt(2);
        String aiPlayer = randomNum == 0 ? Grid.X : Grid.O;

        // X always starts
        turn = Grid.X;

        if (aiPlayer.equals(Grid.X)) {
            view.setTag("1_1");
            changeGrid(view);
            message.setText("AI is playing as X");
        } else {
            message.setText("AI is playing as O");
        }

        ai = new TicTacToeAI(aiPlayer, grid);
    }
}
