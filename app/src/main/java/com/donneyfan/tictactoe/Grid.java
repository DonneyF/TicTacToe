package com.donneyfan.tictactoe;

/**
 * Created by Donney on 3/24/2018.
 */

public class Grid {

    private int[][] grid;
    public static final int X = 1;
    public static final int O = 2;

    public Grid() {
        grid = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }

    public void setX(int x, int y) {
        grid[x][y] = X;
    }

    public void setO(int x, int y) {
        grid[x][y] = O;
    }

    public void clear() {
        grid = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }

    public int getCoord(int x, int y) {
        return grid[x][y];
    }
}
