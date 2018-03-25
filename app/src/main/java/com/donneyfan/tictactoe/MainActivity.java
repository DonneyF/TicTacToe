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

public class MainActivity extends AppCompatActivity {

    // Matrix to keep track of current state
    private Grid grid;
    // Integer to track the current turn
    private int turn;

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
        try {
            if (turn == Grid.X) {
                InputStream stream = getAssets().open("X.png");
                Drawable d = Drawable.createFromStream(stream, null);
                imageButtons.get(x).get(y).setImageDrawable(d);
                grid.setX(x, y);
            } else if (turn == Grid.O) {
                InputStream stream = getAssets().open("O.png");
                Drawable d = Drawable.createFromStream(stream, null);
                imageButtons.get(x).get(y).setImageDrawable(d);
                grid.setO(x, y);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // Check for win condition
        if (checkWin(turn) == turn) {
            if (turn == Grid.O) message.setText("O wins!");
            else message.setText("X wins!");
        }

        // Change turns
        if (turn == Grid.X) turn = Grid.O;
        else turn = Grid.X;
    }

    /**
     * Checks the grid for a win
     * @return 1 if X wins, 2 if O wins, 0 otherwise.
     */
    private int checkWin(int player){
        boolean win = false;

        // Check Horizontal
        for (int i = 0; i < 3; i++) {
            if (grid.getCoord(i, 0) != player) continue;
            for (int j = 1; j < 3; j++){
                // Next element in the row must match the previous row element as well as the right value.
                win = grid.getCoord(i, j) == grid.getCoord(i, j - 1) && grid.getCoord(i, j) == player;
                // Stop checking for a win if we hit a false
                if (!win) break;
            }
            // Check if the current row has a win
            if (win) return player;
        }

        // Check Vertical
        for (int i = 0; i < 3; i++) {
            if (grid.getCoord(0, i) != player) continue;
            for (int j = 1; j < 3; j++){
                // The next element in the column must match the previous column element as well as the right value
                win = grid.getCoord(j, i) == grid.getCoord(j - 1, i) && grid.getCoord(j, i) == player;
                // Stop checking for a win if we hit a false
                if (!win) break;
            }
            // Check if the current column has a win
            if (win) return player;
        }

        // Check main diagonal
        for (int i = 1; i < 3; i++){
            if (grid.getCoord(i - 1,  i - 1) != player) break;
            win = grid.getCoord(i, i) == grid.getCoord(i - 1, i - 1) && grid.getCoord(i, i) == player;
        }
        if (win) return player;

        // Check the anti-diagonal
        for (int i = 1, j = 1; i < 3 && j >= 0; i++, j--){
            if (grid.getCoord(i - 1, j + 1) != player) break;
            win = grid.getCoord(i, j) == grid.getCoord(i - 1, j + 1) && grid.getCoord(i, j) == player;
        }
        if (win) return player;

        // Return 0 if no win
        return 0;
    }
}
