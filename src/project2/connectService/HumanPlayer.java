package project2;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HumanPlayer {

	private int player;
	private int[][] board;
	String previous = "";
	int limitSec;

	public HumanPlayer(boolean humanPlayFirst) {
		this.player = humanPlayFirst ? 1 : -1;
	}

	public void play(Board b) {
		board = b.getBoard();
		b.printBoard();
		String symbol = player > 0 ? "x" : "o";
		System.out.println("Your turn: " + symbol);
		boolean verified = false;
		while (!verified) {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			System.out.print("enter your row: ");
			int i = in.nextInt();
			System.out.print("enter you column: ");
			int j = in.nextInt();
			if (board[i][j] == 0) {
				verified = true;
				b.setBoard(player, i, j);
			}
		}
		System.out.println("Wait for ai to response...");
	}

	public void play(Board b, connectService http) throws Exception {
		board = b.getBoard();
		b.printBoard();
		String symbol = player > 0 ? "x" : "o";
		System.out.println("Other teams turn: " + symbol);
		boolean verified = false;
		limitSec = 120;
		while (!verified) {
			// try to get step from service, should return in 2 minutes
			String move = http.getMoves();
			while (limitSec > 0) {
				System.out.println("remians " + --limitSec + " s");
				if (move.equals(previous) || move == "" || move.length() == 0) {
					System.out
							.print("the step state is not updated, trying to send request again....");
					move = http.getMoves();
				} else {
					System.out.print("the step state is updated");
					previous = http.getMoves();
					break;
				}
				TimeUnit.SECONDS.sleep(1);
			}
			if (limitSec == 0) {
				System.out.print("another team is out of time, we are winner");
				return;
			}
			int index = move.lastIndexOf(",");
			int i = Integer.parseInt(move.substring(0, index));
			int j = Integer.parseInt(move.substring(index + 1, move.length()));
			if (board[i][j] == 0) {
				verified = true;
				b.setBoard(player, i, j);
			}
		}
		System.out.println("Wait for ai to response...");
	}
}
