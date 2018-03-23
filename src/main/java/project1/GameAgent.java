package project1;

public class GameAgent {
    private int[][] board;

    public GameAgent() {
        board = new int[3][3];
    }

    public int[][] getBoard() {
        return board.clone();
    }

    public void setBoard(int chess, int x, int y) {
        if (board[x][y] == 0) {
            board[x][y] = chess;
        } else {
            System.out.println("place chess failure");
        }
    }

    /**
     *
     * @return 1/-1 - win by p1/p2, 0 - game continues, 2 - draw
     */
    public int hasWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0) {
                return board[i][0];
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[0][j] == board[2][j] && board[0][j] != 0) {
                return board[0][j];
            }
        }
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 0) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 0) {
            return board[0][2];
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return 0;
                }
            }
        }
        return 2;
    }

    public void printBoard() {
        System.out.println("---");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("---");
    }
}
