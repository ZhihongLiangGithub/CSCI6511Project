package project2;

public class Board {

    private int[][] board;
    private int numToWin;
    private int[][] p1Space;
    private int[][] p2Space;

    public Board(int size, int numToWin) {
        this.board = new int[size][size];
        this.numToWin = numToWin;
        p1Space = new int[][]{{size, size}, {-1, -1}};
        p2Space = new int[][]{{size, size}, {-1, -1}};
    }

    public int[][] getBoard() {
        return board.clone();
    }

    public int[] getPlayerSpaceTopLeft(int player) {
        if (player == 1) {
            return p1Space[0].clone();
        } else if (player == -1) {
            return p2Space[0].clone();
        }
        return null;
    }

    public int[] getPlayerSpaceBottomRight(int player) {
        if (player == 1) {
            return p1Space[1].clone();
        } else if (player == -1) {
            return p2Space[1].clone();
        }
        return null;
    }

    public int getNumToWin() {
        return numToWin;
    }

    public void setBoard(int player, int x, int y) {
        if (board[x][y] == 0) {
            board[x][y] = player;
            // update the player space
            if (player == 1) {
                // update top left corner
                p1Space[0][0] = Math.min(p1Space[0][0], x);
                p1Space[0][1] = Math.min(p1Space[0][1], y);
                // update bottom right corner
                p1Space[1][0] = Math.max(p1Space[1][0], x);
                p1Space[1][1] = Math.max(p1Space[1][1], y);
            } else if (player == -1) {
                // update top left corner
                p2Space[0][0] = Math.min(p2Space[0][0], x);
                p2Space[0][1] = Math.min(p2Space[0][1], y);
                // update bottom right corner
                p2Space[1][0] = Math.max(p2Space[1][0], x);
                p2Space[1][1] = Math.max(p2Space[1][1], y);
            }
        } else {
            System.out.println("place chess failure");
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
