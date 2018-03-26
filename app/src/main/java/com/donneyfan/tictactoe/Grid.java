package com.donneyfan.tictactoe;

/**
 * Created by Donney on 3/24/2018.
 */

public class Grid {

    private String[][] grid;
    public static final String X = "X";
    public static final String O = "O";

    public Grid() {
        grid = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
    }

    public void setX(int x, int y) {
        grid[x][y] = X;
    }

    public void setO(int x, int y) {
        grid[x][y] = O;
    }

    public void clear() {
        grid = new String[][]{{"", "", ""}, {"", "", ""}, {"", "", ""}};
    }

    public String getCoord(int x, int y) {
        return grid[x][y];
    }
}
