package project3;

public class Test {

    public static void main(String[] args) {
        NPuzzle np = new NPuzzle();
        int[][] g = np.readGridFromFile("src/main/resources/project3/n-puzzle.txt");
        //int[][] g = np.generateNGrid(4);
        np.solve(g);

    }
}
