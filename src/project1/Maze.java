package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {

    int[][] maze; // 0 - space, 1 - block, 2 - path, 3 - destination

    public void buildFromFile(String filePath) {
        File file = new File(filePath);
        List<String> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine().replaceAll("\\s", ""));
            }
            if (list.size() != 0) {
                maze = new int[list.size()][list.get(0).length()];
                for (int i = 0; i < list.size(); i++) {
                    char[] chars = list.get(i).toCharArray();
                    for (int j = 0; j < chars.length; j++) {
                        maze[i][j] = chars[j] - '0';
                    }
                }
                System.out.println("Finish building a maze sized " + maze.length + " X " + maze[0].length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPath(int x1, int y1, int x2, int y2) {
        if (maze == null) {
            System.out.println("Maze is not initialized");
            return false;
        }
        if (x1 < 0 || x1 >= maze[0].length || x2 < 0 || x2 >= maze[0].length
                || y1 < 0 || y1 >= maze.length || y2 < 0 || y2 >= maze.length) {
            System.out.println("NO: Start point or end point is out of boundary");
            return false;
        }
        if (maze[x1][y1] == 1 || maze[x2][y2] == 1) {
            System.out.println("NO: Start point or end point should be on a clear space");
            return false;
        }
        int[][] meizi = maze.clone();
        meizi[x2][y2] = 3;
        boolean result = solve(meizi, x1, y1);
        if (result) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        return result;
    }

    private boolean solve(int[][] meizi, int x, int y) {
        if (meizi[x][y] == 3) {
            return true;
        }
        if (meizi[x][y] == 1 || meizi[x][y] == 2) {
            return false;
        }
        meizi[x][y] = 2;
        if (solve(meizi, x, y + 1)) {
            return true;
        }
        if (solve(meizi, x + 1, y)) {
            return true;
        }
        if (solve(meizi, x, y - 1)) {
            return true;
        }
        if (solve(meizi, x - 1, y)) {
            return true;
        }
        meizi[x][y] = 0;
        return false;
    }
}
