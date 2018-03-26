package com.donneyfan.tictactoe;

public class TicTacToeAI {

    Grid grid;
    int player;
    int[] lastPlayerMove;

    public TicTacToeAI(int player, Grid grid) {
        this.player = player;
        this.grid = grid;
    }

    public void setLastMove(int[] lastPlayerMove) {
        this.lastPlayerMove = lastPlayerMove;
    }

    private boolean playerPlayedEdge() {
        return lastPlayerMove == new int[]{0, 1}
                || lastPlayerMove == new int[]{1, 0}
                || lastPlayerMove == new int[]{2, 1}
                || lastPlayerMove == new int[]{1, 2};
    }

    public void playMove() {
        if (player == Grid.X) {
            // Always start with X in the center

            if (grid.getCoord(1, 1) == 0) {
                grid.setX(1, 1);
                return;
            }

            if (playerPlayedEdge()) {

            }
        } else {
            // AI is O
        }
    }
}
