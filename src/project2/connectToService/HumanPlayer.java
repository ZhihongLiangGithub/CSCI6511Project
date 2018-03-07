import java.util.concurrent.TimeUnit;

public class HumanPlayer {

    private int player;
    private int[][] board;
    String previous = "";

    public HumanPlayer(boolean humanPlayFirst) {
        this.player = humanPlayFirst ? 1 : -1;
    }

    public void play(Board b) throws Exception {
    	connectService http = new connectService();
        board = b.getBoard();
        b.printBoard();
        String symbol = player > 0 ? "x" : "o";
        System.out.println("Other teams turn: " + symbol);
        boolean verified = false;
        int limitSec = 120;
        while (!verified) {
        	String move = http.getMoves();
//        	try to get step from service, should return in 2 minutes
        	while(limitSec > 0){  
                System.out.println("remians "+ --limitSec +" s");
                if(move.equals(previous) || move == "") {
            		System.out.print("the step state is not update, trying to send request again....");
            		move = http.getMoves();
            	}else{
            		System.out.print("the step state is updated");
            		previous = http.getMoves();
            	}
                TimeUnit.SECONDS.sleep(1);
            }  
            if (limitSec == 0) {
            	System.out.print("another team is out of time, we are winner");
            	return;
            }
        	int i = Integer.parseInt(move.substring(0,1));
        	int j = Integer.parseInt(move.substring(2,3));
            if (board[i][j] == 0) {
                verified = true;
                b.setBoard(player, i, j);
            }
        }
        System.out.println("Wait for ai to response...");
    }
}
