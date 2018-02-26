package project2;

import java.util.Scanner;

public class HumanPlayer {

    private int player;
    private int[][] board;

    public HumanPlayer(boolean humanPlayFirst) {
        this.player = humanPlayFirst ? 1 : -1;
    }

    public void play(Board b) {
        board = b.getBoard();
        b.printBoard();
        String symbol = player > 0 ? "x" : "o";
        System.out.println("Your turn: " + symbol);
        Scanner in = new Scanner(System.in);
        boolean verified = false;
        while (!verified) {
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
}
