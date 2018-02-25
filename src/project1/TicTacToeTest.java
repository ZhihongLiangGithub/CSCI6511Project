package project1;

public class TicTacToeTest {

    public static void main(String[] args) {
        TicTacToePlayer p1 = new TicTacToePlayer(true, false);
        TicTacToePlayer p2 = new TicTacToePlayer(false, false);
        int p1Win = 0;
        int p2Win = 0;
        int draw = 0;
        int round = 100;
        for (int i = 0; i < round; i++) {
            GameAgent agent = new GameAgent();
            int gameState = 0;
            boolean turn = true;
            while (gameState == 0) {
                if (turn) {
                    p1.play(agent);
                } else {
                    p2.play(agent);
                }
                turn = !turn;
                gameState = agent.hasWinner();
            }
            if (gameState == 1) {
                p1Win++;
            } else if (gameState == -1) {
                p2Win++;
            } else if (gameState == 2) {
                draw++;
            }
            //agent.printBoard();
        }
        System.out.println("p1 win: " + p1Win + ", p2 win: " + p2Win + ", draw: " + draw);

    }
}
