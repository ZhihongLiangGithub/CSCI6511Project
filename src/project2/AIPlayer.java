package project2;

import java.util.*;

public class AIPlayer {

    private int player;
    private int[][] board;
    private int numToWin;
    private int maxSearchDepth;
    private int round;
    private Evaluator eva;

    private static final int MAX_NUM_TO_WIN = 8;


    public AIPlayer(boolean playFirst, int numToWin, int maxSearchDepth) {
        this.player = playFirst ? 1 : -1;
        this.numToWin = Math.min(numToWin, MAX_NUM_TO_WIN);
        this.maxSearchDepth = maxSearchDepth;
        this.round = 0;
        this.eva = new Evaluator(numToWin, player);

    }

    public void play(Board b) {
        board = b.getBoard();
        round++;
        int bestRow = -1;
        int bestCol = -1;

        double bestVal = Integer.MIN_VALUE;
        double currScore = eva.evaluateWholeBoard(board);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = player;
                    double s = eva.evaluateBoard(board, currScore, player, i, j);
                    double moveVal = minimax(board, s, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board[i][j] = 0;
                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }

        }
        String symbol = player > 0 ? "x" : "o";
        System.out.println("AIPlayer: " + symbol + ", plays at [" + bestRow + ", " + bestCol + "] " + round);
        b.setBoard(player, bestRow, bestCol);
        b.printBoard();
        System.out.println();
    }

    private double minimax(int[][] board, double score, int depth, double alpha, double beta, boolean isMax) {
        //double score = evaluate(board);
        //double score = 0;
        if (score == Evaluator.SCORE_WHEN_WIN) {
            return score - depth;
        }
        if (score == -Evaluator.SCORE_WHEN_WIN) {
            return score + depth;
        }
        if (depth == maxSearchDepth) {
            Random random = new Random();
            return score + (random.nextDouble() - 0.5) * 0.01;
        }
        if (isMax) {
            double best = Integer.MIN_VALUE;
            boolean isMoveLeft = false;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0) {
                        isMoveLeft = true;
                        board[i][j] = player;
                        double s = eva.evaluateBoard(board, score, player, i, j);
                        best = Math.max(best, minimax(board, s, depth + 1, alpha, beta, !isMax));
                        board[i][j] = 0;
                        alpha = Math.max(alpha, best);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            if (!isMoveLeft) return 0;
            return best;
        } else {
            double best = Integer.MAX_VALUE;
            boolean isMoveLeft = false;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0) {
                        isMoveLeft = true;
                        board[i][j] = -player;
                        double s = eva.evaluateBoard(board, score, -player, i, j);
                        best = Math.min(best, minimax(board, s, depth + 1, alpha, beta, !isMax));
                        board[i][j] = 0;
                        beta = Math.min(beta, best);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            if (!isMoveLeft) return 0;
            return best;
        }
    }

}
