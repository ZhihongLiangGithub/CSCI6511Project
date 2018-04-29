package project3;

import com.google.common.base.Splitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import project3.Node.MoveDirection;

public class NPuzzle {

    private long startTime;
    private long endTime;

    private static final int FOUND = -1;


    public NPuzzle() {

    }

    public void solve(int[][] grid) {
        int threshold = Heuristic.evaluate(grid);
        Node start = new Node(grid, getInitialZeroPos(grid), null, MoveDirection.NULL);
        startTime = System.currentTimeMillis();
        while (true) {
            int temp = IDAstarSearch(start, 0, threshold);
            if (temp == FOUND) {
                return;
            }
            threshold = temp;
        }
    }

    // iterative deepening A* search algorithm
    private int IDAstarSearch(Node node, int g, int threshold) {
        int f = g + node.getH();
        if (f > threshold) return f;
        if (node.getH() == 0) {
            endTime = System.currentTimeMillis();
            System.out.println("Solved! Steps: " + g + "; Time: " + (endTime - startTime) + "ms");
            Node n = node;
            while (n != null) {
                printGrid(n.getGrid());
                System.out.println();
                n = n.getPrev();
            }
            return FOUND;
        }
        int min = Integer.MIN_VALUE;
        for (Node n : getNextNodes(node)) {
            int temp = IDAstarSearch(n, g + 1, threshold);
            if (temp == FOUND) return FOUND;
            if (min < temp) min = temp;
        }
        return min;
    }

    private int[] getInitialZeroPos(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) return new int[]{i, j};
            }
        }
        return null;
    }

    public List<Node> getNextNodes(Node node) {
        List<Node> list = new ArrayList<>();
        MoveDirection prevMove = node.getPrevMove();
        int[] zeroPos = node.getZeroPos();
        int[][] grid = node.getGrid();
        if (!prevMove.equals(Node.MoveDirection.UP) && zeroPos[0] > 0) { // can move down
            int[][] g = node.getGrid();
            int temp = g[zeroPos[0] - 1][zeroPos[1]]; // tile to move
            g[zeroPos[0]][zeroPos[1]] = temp; // move tile
            g[zeroPos[0] - 1][zeroPos[1]] = 0;
            list.add(new Node(g, new int[]{zeroPos[0] - 1, zeroPos[1]}, node, MoveDirection.DOWN));
        }
        if (!prevMove.equals(Node.MoveDirection.DOWN) && zeroPos[0] < grid.length - 1) { // can move up
            int[][] g = node.getGrid();
            int temp = g[zeroPos[0] + 1][zeroPos[1]];
            g[zeroPos[0]][zeroPos[1]] = temp;
            g[zeroPos[0] + 1][zeroPos[1]] = 0;
            list.add(new Node(g, new int[]{zeroPos[0] + 1, zeroPos[1]}, node, MoveDirection.UP));

        }
        if (!prevMove.equals(Node.MoveDirection.LEFT) && zeroPos[1] > 0) { // can move right
            int[][] g = node.getGrid();
            int temp = g[zeroPos[0]][zeroPos[1] - 1];
            g[zeroPos[0]][zeroPos[1]] = temp;
            g[zeroPos[0]][zeroPos[1] - 1] = 0;
            list.add(new Node(g, new int[]{zeroPos[0], zeroPos[1] - 1}, node, MoveDirection.RIGHT));

        }
        if (!prevMove.equals(Node.MoveDirection.RIGHT) && zeroPos[1] < grid[0].length - 1) { // can move left
            int[][] g = node.getGrid();
            int temp = g[zeroPos[0]][zeroPos[1] + 1];
            g[zeroPos[0]][zeroPos[1]] = temp;
            g[zeroPos[0]][zeroPos[1] + 1] = 0;
            list.add(new Node(g, new int[]{zeroPos[0], zeroPos[1] + 1}, node, MoveDirection.LEFT));

        }
        return list;
    }

    public int[][] generateNGrid(int n) {
        int[][] g = new int[n][n];
        List<Integer> nums = new ArrayList<>();
        int max = n * n;
        for (int i = 0; i < max; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                g[i][j] = nums.get(i * n + j);
            }
        }
        printGrid(g);
        System.out.println("Finish generating a " + g.length + "*" + g[0].length + " grid");
        return g;
    }

    public int[][] readGridFromFile(String filePath) {
        File file = new File(filePath);
        List<String> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine().replaceAll("[^0-9]", ","));
            }
            if (list.size() != 0) {
                int[][] g = new int[list.size()][list.size()];
                for (int i = 0; i < g.length; i++) {
                    List<String> nums = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(list.get(i));
                    for (int j = 0; j < g[0].length; j++) {
                        g[i][j] = Integer.valueOf(nums.get(j));
                    }
                }
                printGrid(g);
                return g;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printGrid(int[][] grid) {
        if (grid == null || grid.length == 0) return;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%3d", grid[i][j]);
                System.out.printf("%3s", " ");
            }
            System.out.println();
        }
    }
}
