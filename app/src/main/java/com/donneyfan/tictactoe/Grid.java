package com.donneyfan.tictactoe;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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

    public Grid(String[][] grid){
        this.grid = grid;
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

    /**
     * @return A list of coordiates for available moves.
     */
    public ArrayList<int[]> availableMoves() {
        ArrayList<int[]> moves = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].isEmpty()) moves.add(new int[]{i, j});
            }
        }

        return moves;
    }

    public String getCoord(int x, int y) {
        return grid[x][y];
    }

    public String[][] getArr() {
        return grid.clone();
    }

    public Grid duplicate(){
        // Create a copy

        String[][] newGrid = new String[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, grid[0].length);
        }

        return new Grid(newGrid);
    }

    /**
     * Checks the grid for a win
     * @return 1 if X wins, 2 if O wins, 0 otherwise.
     */
    public static String checkWin(Grid grid, String player){
        boolean win = false;

        // Check Horizontal
        for (int i = 0; i < 3; i++) {
            if (!grid.getCoord(i, 0).equals(player)) continue;
            for (int j = 1; j < 3; j++){
                // Next element in the row must match the previous row element as well as the right value.
                win = grid.getCoord(i, j).equals(grid.getCoord(i, j - 1)) && grid.getCoord(i, j).equals(player);
                // Stop checking for a win if we hit a false
                if (!win) break;
            }
            // Check if the current row has a win
            if (win) return player;
        }

        // Check Vertical
        for (int i = 0; i < 3; i++) {
            if (!grid.getCoord(0, i).equals(player)) continue;
            for (int j = 1; j < 3; j++){
                // The next element in the column must match the previous column element as well as the right value
                win = grid.getCoord(j, i).equals(grid.getCoord(j - 1, i)) && grid.getCoord(j, i).equals(player);
                // Stop checking for a win if we hit a false
                if (!win) break;
            }
            // Check if the current column has a win
            if (win) return player;
        }

        // Check main diagonal
        for (int i = 1; i < 3; i++){
            if (!grid.getCoord(i - 1,  i - 1).equals(player)) break;
            win = grid.getCoord(i, i).equals(grid.getCoord(i - 1, i - 1)) && grid.getCoord(i, i).equals(player);
        }
        if (win) return player;

        // Check the anti-diagonal
        for (int i = 1, j = 1; i < 3 && j >= 0; i++, j--){
            if (!grid.getCoord(i - 1, j + 1).equals(player)) break;
            win = grid.getCoord(i, j).equals(grid.getCoord(i - 1, j + 1)) && grid.getCoord(i, j).equals(player);
        }
        if (win) return player;

        // Return blank if no win
        return "";
    }

    public boolean isFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].isEmpty()) return false;
            }
        }
        return true;
    }
}
