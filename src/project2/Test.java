package project2;

public class Test {

    public static void main(String[] args) {
        int n = 15;
        int m = 5;
        AIPlayer p1 = new AIPlayer(true, m, 2);
        AIPlayer p2 = new AIPlayer( false, m, 2);

        int p1Win = 0;
        int p2Win = 0;
        int draw = 0;
        int round = 1;
        for (int i = 0; i < round; i++) {
            Board b = new Board(n,m);
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
                p1Win++;
            } else if (gameState == -1) {
                p2Win++;
            } else if (gameState == 2) {
                draw++;
            }
            //b.printBoard();
        }

        System.out.println("p1 win: " + p1Win + ", p2 win: " + p2Win + ", draw: " + draw);
    }
}
