package project1;

public class MazeTest {
    public static void main(String[] args) {
        Maze m = new Maze();
        m.buildFromFile("src/main/resources/project1/maze.txt");
        m.hasPath(1, 34, 15, 47);
        m.hasPath(1, 2, 3, 39);
        m.hasPath(0, 0, 3, 77);
        m.hasPath(1, 75, 8, 79);
        m.hasPath(1, 75, 39, 40);
        m.hasPath(4, 20, 79, 66);
    }
}
