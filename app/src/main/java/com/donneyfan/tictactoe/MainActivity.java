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
    private GridView gridView;
    private GridViewAdapter gridAdapter;

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

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_layout, getData());
        gridView.setAdapter(gridAdapter);

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
                turn = Grid.O;
            } else if (turn == Grid.O) {
                InputStream stream = getAssets().open("O.png");
                Drawable d = Drawable.createFromStream(stream, null);
                imageButtons.get(x).get(y).setImageDrawable(d);
                grid.setO(x, y);
                turn = Grid.X;
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        if (checkWin(Grid.X) == Grid.X) {
            message.setText("X wins!");
        } /*else if (checkWin(Grid.O) == Grid.O){
            message.setText("O has won! Tap to start a new game");
        }*/
    }

    // Prepare some dummy data for gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    /**
     * Checks the grid for a win
     * @return 1 if X wins, 2 if O wins, 0 otherwise.
     */
    private int checkWin(int player){

        // Assume a win is here. Check otherwise.
        boolean win = true;

        // Check horizontal wins
        for (int i = 0; i < 3; i++){
            if (grid.getCoord(0, i) == 0) continue;
            for (int j = 1; j < 3; j++){
                if (grid.getCoord(i, j) != grid.getCoord(i, j - 1)) {
                    win = false;
                    break;
                }
            }
        }
        if (win) return player;

        return 0;

/*        // Check horizontal wins
        win = true;
        for (int j = 0; j < 3; j++){
            for (int i = 1; i < 3; i++){
                if (grid.getCoord(i, j) != grid.getCoord(i - 1, j) || grid.getCoord(i, j) != player) {
                    win = false;
                    break;
                }
            }
            if (win) return player;
        }


        // Check vertical wins

        for (int j = 0; j < 3; j++){
            count = 0;
            for (int i = 0; i < 3; i++){
                count += grid.getCoord(i, j);
            }
        }*/

/*        if (count == 3) return Grid.X;
        if (count == 6) return Grid.O;

        count = 0;
        // Check main diagonal win
        for (int i = 0; i < 3; i++){
            count += grid.getCoord(i, i);
        }

        if (count == 3) return Grid.X;
        if (count == 6) return Grid.O;

        // Check other diagonal wins
        for (int i = 2; i >= 0; i--){
            count = 0;
            for (int j = 0; j < 3; j++){
                count += grid.getCoord(i, j);
            }
        }

        if (count == 3) return Grid.X;
        if (count == 6) return Grid.O;

        return 0;*/
    }
}
