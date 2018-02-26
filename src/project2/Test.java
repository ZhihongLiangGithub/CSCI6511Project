package project2;

public class Test {

    private int boardSize;
    private int numToWin;

    public static void main(String[] args) {
        // test (18, 8) (15, 7) (12, 6)
        int n = 15;
        int m = 5;
        Test t = new Test(n, m);
        //t.playGameAiVsAi(4, 16, 4, 16);
        t.playGameAiVsHuman(8, 8, true);
    }

    public Test(int boardSize, int numToWin) {
        this.boardSize = boardSize;
        this.numToWin = numToWin;
    }

    public void playGameAiVsHuman(int aiMaxSearchDepth, int aiMaxSearchSpace, boolean humanPlayFirst) {
        AIPlayer ai = new AIPlayer(!humanPlayFirst, numToWin, aiMaxSearchDepth, aiMaxSearchSpace);
        HumanPlayer human = new HumanPlayer(humanPlayFirst);
        Board b = new Board(boardSize, numToWin);
        int gameState = 0;
        boolean turn = humanPlayFirst;
        while (gameState == 0) {
            if (turn) {
                human.play(b);
            } else {
                ai.play(b);
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

    public void playGameAiVsAi(int p1MaxSearchDepth, int p1MaxSearchSpace, int p2MaxSearchDepth, int p2MaxSearchSpace) {
        AIPlayer p1 = new AIPlayer(true, numToWin, p1MaxSearchDepth, p1MaxSearchSpace);
        AIPlayer p2 = new AIPlayer(false, numToWin, p2MaxSearchDepth, p2MaxSearchSpace);
        Board b = new Board(boardSize, numToWin);
        int gameState = 0;
        boolean turn = true;
        while (gameState == 0) {
            if (turn) {
                p1.play(b);
            } else {
                p2.play(b);
            }
            turn = !turn;
            gameState = b.getGameState();
        }
        if (gameState == 1) {
            System.out.println("p1 win");
        } else if (gameState == -1) {
            System.out.println("p2 win");
        } else if (gameState == 2) {
            System.out.println("draw");
        }
    }
}
