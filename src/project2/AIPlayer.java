package project2;

import java.util.*;

public class AIPlayer {

    private int player;
    private int[][] board;
    private int numToWin;
    private int maxSearchDepth;
    private int round; // a simple counter for the round of the game
    private Evaluator eva;

    private static final int MAX_SEARCH_SPACE = 16;


    public AIPlayer(boolean playFirst, int numToWin, int maxSearchDepth) {
        this.player = playFirst ? 1 : -1;
        this.numToWin = numToWin;
        this.maxSearchDepth = maxSearchDepth;
        this.round = 0;
        this.eva = new Evaluator(numToWin, player);
    }

    public void play(Board b) {
        board = b.getBoard();
        round++;
        double currScore = eva.evaluateWholeBoard(board);
        List<PointScore> pointScoreList = getValuablePlaces(board, currScore, player);
        int bestRow = -1;
        int bestCol = -1;
        double bestVal = Integer.MIN_VALUE;
        int searchSpace = Math.min(MAX_SEARCH_SPACE, pointScoreList.size());
        for (int k = 0; k < searchSpace; k++) {
            int i = pointScoreList.get(k).getI();
            int j = pointScoreList.get(k).getJ();
            double score = pointScoreList.get(k).getScore();
            board[i][j] = player;
            double moveVal = minimax(board, score, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            board[i][j] = 0;
            if (moveVal > bestVal) {
                bestRow = i;
                bestCol = j;
                bestVal = moveVal;
            }
        }
        b.setBoard(player, bestRow, bestCol);
        b.printBoard();
        String symbol = player > 0 ? "x" : "o";
        System.out.println("AIPlayer: " + symbol + ", plays at [" + bestRow + ", " + bestCol + "] " + round);
        System.out.println();
    }

    /**
     * evaluate the board and return the score of possible places from high to low
     *
     * @param board
     * @param currScore
     * @param chess
     * @return
     */
    private List<PointScore> getValuablePlaces(int[][] board, double currScore, int chess) {
        List<PointScore> pointScoreList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = chess;
                    double score = eva.evaluateBoard(board, currScore, chess, i, j);
                    pointScoreList.add(new PointScore(i, j, score));
                    board[i][j] = 0;
                }
            }
        }
        if (chess * player > 0) {
            Collections.sort(pointScoreList, Comparator.comparingDouble(PointScore::getScore).reversed());
        } else if (chess * player < 0) {
            Collections.sort(pointScoreList, Comparator.comparingDouble(PointScore::getScore));
        }
        return pointScoreList;
    }

    private double minimax(int[][] board, double score, int depth, double alpha, double beta, boolean isMax) {
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
            List<PointScore> pointScoreList = getValuablePlaces(board, score, player);
            int searchSpace = Math.min(MAX_SEARCH_SPACE, pointScoreList.size());
            if (searchSpace == 0) return 0;
            double best = Integer.MIN_VALUE;
            for (int k = 0; k < searchSpace; k++) {
                int i = pointScoreList.get(k).getI();
                int j = pointScoreList.get(k).getJ();
                double s = pointScoreList.get(k).getScore();
                board[i][j] = player;
                best = Math.max(best, minimax(board, s, depth + 1, alpha, beta, !isMax));
                board[i][j] = 0;
                alpha = Math.max(alpha, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        } else {
            List<PointScore> pointScoreList = getValuablePlaces(board, score, -player);
            int searchSpace = Math.min(MAX_SEARCH_SPACE, pointScoreList.size());
            if (searchSpace == 0) return 0;
            double best = Integer.MAX_VALUE;
            for (int k = 0; k < searchSpace; k++) {
                int i = pointScoreList.get(k).getI();
                int j = pointScoreList.get(k).getJ();
                double s = pointScoreList.get(k).getScore();
                board[i][j] = -player;
                best = Math.min(best, minimax(board, s, depth + 1, alpha, beta, !isMax));
                board[i][j] = 0;
                beta = Math.min(beta, best);
                if (beta <= alpha) {
                    break;
                }
            }
            return best;
        }
    }

}
