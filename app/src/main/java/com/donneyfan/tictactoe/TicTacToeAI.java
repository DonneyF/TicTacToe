package com.donneyfan.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TicTacToeAI {

    private Grid grid;
    public String player;
    public String opponent;
    private int[] nextMove;

    public TicTacToeAI(String player, Grid grid) {
        this.player = player;
        this.grid = grid;
        opponent = player.equals(Grid.X) ? Grid.O : Grid.X;

        // Start turn if AI is X
        if (player.equals(Grid.X)) {
            // Always start in the center.
            grid.setX(1, 1);
        }
    }

    public int[] playMove() {
        minimax(grid, player, 0);
        return nextMove;
    }


    public int minimax(Grid grid, String turn, int depth) {
        if (grid.isFull()) return utility(grid, depth);
        depth++;

        LinkedList<Integer> scores = new LinkedList<>();
        LinkedList<int[]> moves = new LinkedList<>();

        // Populate all possible scores for this current state
        for (int[] move : grid.availableMoves()) {
            Grid possibleState = grid.duplicate();
            String nextTurn = turn.equals(Grid.X) ? Grid.O : Grid.X;

            if (turn.equals(Grid.X)) possibleState.setX(move[0], move[1]);
            else possibleState.setO(move[0], move[1]);

            scores.add(minimax(possibleState, nextTurn, depth));

            moves.add(move);
        }

        // Get the maximum if the current turn is the AI
        int index = 0;
        int counter = scores.get(0);
        if (turn.equals(player)) {
            // Get the index in the array of the largest score
            for (int i = 1; i < scores.size(); i++) {
                if (scores.get(i) > counter) {
                    index = i;
                    counter = scores.get(i);
                }
            }
            // Minimize AI loss if it is the human's turn.
        } else {
            // Get the index in the array of the smallest score
            for (int i = 1; i < scores.size(); i++) {
                if (scores.get(i) < counter) {
                    index = i;
                    counter = scores.get(i);
                }
            }
        }

        nextMove = moves.get(index);
        return scores.get(index);
    }

    /**
     * The objective function
     * @param grid the current state of the board
     * @return 10 if it is a win for the AI. 0 if tie. -10 if loss.
     */
    public int utility(Grid grid, int depth) {
        if (Grid.checkWin(grid, player)) {
            return 10 - depth;
        } else if (Grid.checkWin(grid, opponent)) {
            return depth - 10;
        } else return 0;
    }
}
