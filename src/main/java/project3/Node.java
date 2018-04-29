package project3;



public class Node {

    private int[][] grid;
    private int h;
    private int[] zeroPos;
    private Node prev;
    private MoveDirection prevMove;

    public Node(int[][] grid, int[] zeroPos, Node prev, MoveDirection prevMove) {
        this.grid = grid;
        this.h = Heuristic.evaluate(grid);
        this.zeroPos = zeroPos;
        this.prev = prev;
        this.prevMove = prevMove;
    }

    public int getH() {
        return h;
    }

    public int[][] getGrid() {
        int[][] g = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            g[i] = grid[i].clone();
        }
        return g;
    }

    public int[] getZeroPos(){
        return zeroPos.clone();
    }

    public Node getPrev() {
        return prev;
    }

    public MoveDirection getPrevMove() {
        return prevMove;
    }



    enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, NULL;
    }
}
