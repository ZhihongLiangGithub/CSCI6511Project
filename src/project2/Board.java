package project2;

public class Board {

    private int[][] board;
    private int numToWin;
    private static final int MAX_SIZE = 20;
    private static final int MAX_NUM_TO_WIN = 8;

    public Board(int size, int numToWin) {
        int boardSize = Math.min(size, MAX_SIZE);
        this.board = new int[boardSize][boardSize];
        this.numToWin = Math.min(numToWin, MAX_NUM_TO_WIN);
    }

    public int[][] getBoard() {
        return board.clone();
    }

    public int getNumToWin() {
        return numToWin;
    }

    public void setBoard(int chess, int x, int y) {
        if (board[x][y] == 0) {
            board[x][y] = chess;
        } else {
            System.out.println("place chess failure");
        }
    }

    public void setBoardTest(int[][] b) {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                this.board[i][j] = b[i][j];
            }
        }
    }

    public void printBoard() {
        System.out.printf("%2s", " ");
        for (int j = 0; j < board[0].length; j++) {
            System.out.printf("%2d", j);
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] == 1) {
                    System.out.printf("%2s", "x");
                } else if (board[i][j] == -1) {
                    System.out.printf("%2s", "o");
                } else {
                    System.out.printf("%2s", "_");
                }
            }
            System.out.println();
        }
    }

    /**
     * @return 1/-1 - win by p1/p2, 0 - game continues, 2 - draw
     */
    public int getGameState() {
        for (int i = 0; i < board.length; i++) {
            int prev = 0;
            int count = 0;
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    prev = 0;
                    count = 0;
                } else {
                    if (prev != board[i][j]) {
                        count = board[i][j];
                    } else {
                        count += board[i][j];
                    }
                    prev = board[i][j];
                }
                if (Math.abs(count) == numToWin) return Integer.signum(count);
            }
        }
        for (int j = 0; j < board[0].length; j++) {
            int prev = 0;
            int count = 0;
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == 0) {
                    prev = 0;
                    count = 0;
                } else {
                    if (prev != board[i][j]) {
                        count = board[i][j];
                    } else {
                        count += board[i][j];
                    }
                    prev = board[i][j];
                }
                if (Math.abs(count) == numToWin) return Integer.signum(count);
            }
        }
        int diagLength = 2 * board.length - 1;
        int row = numToWin - 1;
        int col = 0;
        for (int k = numToWin - 1; k < diagLength - numToWin + 1; k++) {
            int prev = 0;
            int count = 0;
            int i = row;
            int j = col;
            while (i >= 0 && j < board[0].length) {
                if (board[i][j] == 0) {
                    prev = 0;
                    count = 0;
                } else {
                    if (prev != board[i][j]) {
                        count = board[i][j];
                    } else {
                        count += board[i][j];
                    }
                    prev = board[i][j];
                }
                if (Math.abs(count) == numToWin) return Integer.signum(count);
                i--;
                j++;
            }
            if (row < board.length - 1) {
                row++;
            } else if (col < board[0].length - numToWin) {
                col++;
            }
        }
        row = board.length - numToWin;
        col = 0;
        for (int k = numToWin - 1; k < diagLength - numToWin + 1; k++) {
            int prev = 0;
            int count = 0;
            int i = row;
            int j = col;
            while (i < board.length && j < board[0].length) {
                if (board[i][j] == 0) {
                    prev = 0;
                    count = 0;
                } else {
                    if (prev != board[i][j]) {
                        count = board[i][j];
                    } else {
                        count += board[i][j];
                    }
                    prev = board[i][j];
                }
                if (Math.abs(count) == numToWin) return Integer.signum(count);
                i++;
                j++;
            }
            if (row > 0) {
                row--;
            } else if (col < board[0].length - numToWin) {
                col++;
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    return 0;
                }
            }
        }
        return 2;
    }
}
