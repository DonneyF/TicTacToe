package com.donneyfan.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    int[][] grid;

    private int checkWin(int player){
        // Assume a win is here. Check otherwise.
        boolean win = false;

        // Check vertical
        for (int i = 0; i < 3; i++) {
            if (grid[0][i] == 0) continue;
            for (int j = 1; j < 3; j++){
                if (grid[i][j] == grid[i][j - 1] && grid[i][j] == player) {
                    win = true;
                    break;
                }
            }
        }
        if (win) return player;

        return 0;
    }

    @Test
    public void test1 () {
        grid = new int[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};

        assertEquals(1, checkWin(1));
    }
}