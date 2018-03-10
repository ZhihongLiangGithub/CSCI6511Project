package project2.connectService;

import java.util.*;

import project2.Board;
import project2.Evaluator;
import project2.PointScore;


public class AIPlayer {

	private int player;
	private int[][] board;
	private int maxSearchDepth;
	private int maxSearchSpace;
	private int round; // a simple counter for the round of the game
	private Evaluator eva;

	public AIPlayer(boolean playFirst, int numToWin, int maxSearchDepth,
			int maxSearchSpace) {
		this.player = playFirst ? 1 : -1;
		this.maxSearchDepth = maxSearchDepth;
		this.maxSearchSpace = maxSearchSpace;
		this.round = 0;
		this.eva = new Evaluator(numToWin, player);
	}

	public void play(Board b) {
		board = b.getBoard();
		round++;
		double currScore = eva.evaluateWholeBoard(board);
		List<PointScore> pointScoreList = eva.getValuablePlaces(board,
				currScore, player);
		int bestRow = -1;
		int bestCol = -1;
		double bestVal = Integer.MIN_VALUE;
		int searchSpace = Math.min(maxSearchSpace, pointScoreList.size());
		for (int k = 0; k < searchSpace; k++) {
			int i = pointScoreList.get(k).getRow();
			int j = pointScoreList.get(k).getColumn();
			double score = pointScoreList.get(k).getScore();
			board[i][j] = player;
			double moveVal = minimax(board, score, 0, Integer.MIN_VALUE,
					Integer.MAX_VALUE, false);
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
		System.out.println("AIPlayer: " + symbol + ", plays at [" + bestRow
				+ ", " + bestCol + "] " + round);
		System.out.println();
	}

	private double minimax(int[][] board, double score, int depth,
			double alpha, double beta, boolean isMax) {
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
			List<PointScore> pointScoreList = eva.getValuablePlaces(board,
					score, player);
			int searchSpace = Math.min(maxSearchSpace, pointScoreList.size());
			if (searchSpace == 0)
				return 0;
			double best = Integer.MIN_VALUE;
			for (int k = 0; k < searchSpace; k++) {
				int i = pointScoreList.get(k).getRow();
				int j = pointScoreList.get(k).getColumn();
				double s = pointScoreList.get(k).getScore();
				board[i][j] = player;
				best = Math.max(best,
						minimax(board, s, depth + 1, alpha, beta, !isMax));
				board[i][j] = 0;
				alpha = Math.max(alpha, best);
				if (beta <= alpha) {
					break;
				}
			}
			return best;
		} else {
			List<PointScore> pointScoreList = eva.getValuablePlaces(board,
					score, -player);
			int searchSpace = Math.min(maxSearchSpace, pointScoreList.size());
			if (searchSpace == 0)
				return 0;
			double best = Integer.MAX_VALUE;
			for (int k = 0; k < searchSpace; k++) {
				int i = pointScoreList.get(k).getRow();
				int j = pointScoreList.get(k).getColumn();
				double s = pointScoreList.get(k).getScore();
				board[i][j] = -player;
				best = Math.min(best,
						minimax(board, s, depth + 1, alpha, beta, !isMax));
				board[i][j] = 0;
				beta = Math.min(beta, best);
				if (beta <= alpha) {
					break;
				}
			}
			return best;
		}
	}

	// this functiom is used in service connection model
	public void play(Board b, connectService http) throws Exception {
		board = b.getBoard();
		round++;
		double currScore = eva.evaluateWholeBoard(board);
		List<PointScore> pointScoreList = eva.getValuablePlaces(board,
				currScore, player);
		int bestRow = -1;
		int bestCol = -1;
		double bestVal = Integer.MIN_VALUE;
		int searchSpace = Math.min(maxSearchSpace, pointScoreList.size());
		for (int k = 0; k < searchSpace; k++) {
			int i = pointScoreList.get(k).getRow();
			int j = pointScoreList.get(k).getColumn();
			double score = pointScoreList.get(k).getScore();
			board[i][j] = player;
			double moveVal = minimax(board, score, 0, Integer.MIN_VALUE,
					Integer.MAX_VALUE, false);
			board[i][j] = 0;
			if (moveVal > bestVal) {
				bestRow = i;
				bestCol = j;
				bestVal = moveVal;
			}
		}
		b.setBoard(player, bestRow, bestCol);
		// send this step to service
		StringBuilder sb = new StringBuilder();
		sb.append(bestRow).append(",").append(bestCol);
		http.makeMove(sb.toString());
		b.printBoard();
		String symbol = player > 0 ? "x" : "o";
		System.out.println("AIPlayer: " + symbol + ", plays at [" + bestRow
				+ ", " + bestCol + "] " + round);
		System.out.println();
	}
}
