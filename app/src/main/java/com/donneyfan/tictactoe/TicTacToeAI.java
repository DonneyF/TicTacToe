package com.donneyfan.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class TicTacToeAI {

    private Grid grid;
    public String player;
    private String turn;
    private int[] nextMove;

    public TicTacToeAI(String player, Grid grid) {
        this.player = player;
        this.grid = grid;

        // Start turn if AI is X
        if (player.equals(Grid.X)) {
            turn = Grid.X;
            playStartingMoveX();
        } else turn = Grid.O;
    }

    public int[] playMove(Grid grid) {
        minimax(grid, player);
        return nextMove;
    }

    public void playStartingMoveX() {
        grid.setX(1, 1);
    }

    public int minimax(Grid grid, String turn) {
        if (grid.isFull()) return utility(grid);

        LinkedList<Integer> scores = new LinkedList<>();
        LinkedList<int[]> moves = new LinkedList<>();

        // Populate all possible scores for this current state
        ArrayList<int[]> possibleMoves = grid.availableMoves();
        for (int[] move : possibleMoves) {
            Grid possibleState = grid.duplicate();
            String nextTurn = turn.equals(Grid.X) ? Grid.O : Grid.X;

            if (turn.equals(Grid.X)) possibleState.setX(move[0], move[1]);
            else possibleState.setO(move[0], move[1]);

            scores.add(minimax(possibleState, nextTurn));
            moves.add(move);
        }

        // Calculate the minimum and maximum calculation

        // Get the maximum if the current turn is the AI
        int index = 0;
        if (turn.equals(player)) {
            // Get the index in the array of the largest score
            int counter = scores.get(0);
            for (int i = 1; i < scores.size(); i++) {
                if (i == scores.size()) break;
                if (scores.get(i) == 10) {
                    index = i;
                    break;
                }
                if (scores.get(i) > counter) {
                    index = i;
                    counter = scores.get(i);
                }
            }
        } else {
            // Get the index in the array of the smallest score
            int counter = scores.get(0);
            for (int i = 1; i < scores.size(); i++) {
                if (i == scores.size()) break;
                if (scores.get(i) == 10) {
                    index = i;
                    break;
                }
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
    public int utility(Grid grid) {
        String state = Grid.checkWin(grid, player);
        if (state.equals(player)) {
            return 10;
        } else if (state.equals("")) {
            return 0;
        } else return -10;
    }
}
