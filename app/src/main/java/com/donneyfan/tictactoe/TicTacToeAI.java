package com.donneyfan.tictactoe;

public class TicTacToeAI {

    private Grid grid;
    private String player;
    private int[] lastPlayerMove;

    public TicTacToeAI(String player, Grid grid) {
        this.player = player;
        this.grid = grid;
    }

    public void updateLastMove(int[] lastPlayerMove) {
        this.lastPlayerMove = lastPlayerMove;
    }

    /**
     * Determines if the opposing player played a move on one of the non-corner, non center spaces
     * @return
     */
    private boolean playerPlayedEdge() {
        return lastPlayerMove == new int[]{0, 1}
                || lastPlayerMove == new int[]{1, 0}
                || lastPlayerMove == new int[]{2, 1}
                || lastPlayerMove == new int[]{1, 2};
    }

    public void playMove() {
        if (player == Grid.X) {
            // Always start with X in the center

            if (grid.getCoord(1, 1).isEmpty()) {
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
