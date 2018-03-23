package project2.connectService;

import project2.Board;

public class Test {

	private int boardSize;
	private int numToWin;

	public static void main(String[] args) throws Exception {
		// test (18, 8) (15, 7) (12, 6)
		int n = 15;
		int m = 7;
		Test t = new Test(n, m);
		// t.playGameAiVsAi(4, 16, 4, 16);
		t.playGameAiVsHuman(4, 40, false, true);
	}

	public Test(int boardSize, int numToWin) {
		this.boardSize = boardSize;
		this.numToWin = numToWin;
	}

	public void playGameAiVsHuman(int aiMaxSearchDepth, int aiMaxSearchSpace,
			boolean humanPlayFirst, boolean connectService) throws Exception {
		AIPlayer ai = new AIPlayer(!humanPlayFirst, numToWin, aiMaxSearchDepth,
				aiMaxSearchSpace);
		HumanPlayer human = new HumanPlayer(humanPlayFirst);
		Board b = new Board(boardSize, numToWin);
		connectService http = new connectService();
		int gameState = 0;
		boolean turn = humanPlayFirst;
		while (gameState == 0) {
			if (turn) {
				if (connectService) {
					human.play(b, http);
					if (human.limitSec == 0) break;
				} else {
					human.play(b);
				}
			} else {
				if (connectService) {
					ai.play(b, http);
				} else {
					ai.play(b);
				}
			}
			turn = !turn;
			gameState = b.getGameState();
		}
		if (gameState == 1) {
			if (humanPlayFirst) {
				System.out.println("you win!");
			} else {
				System.out.println("ai win");
			}
		} else if (gameState == -1) {
			if (humanPlayFirst) {
				System.out.println("ai win");
			} else {
				System.out.println("you win!");
			}
		} else if (gameState == 2) {
			System.out.println("draw");
		}
	}
}
