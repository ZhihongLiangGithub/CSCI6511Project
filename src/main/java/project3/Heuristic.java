package project3;


public class Heuristic {


    public static int evaluate(int[][] grid) {
        int n = grid.length;
        // calculate the Manhattan distance
        int manhattan = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) continue;
                manhattan += Math.abs((grid[i][j] - 1) / n - i) + Math.abs((grid[i][j] - 1) % n - j);
            }
        }
        // calculate the linear conflicts
        int conflicts = 0;
        // count the conflicts in rows
        for (int i = 0; i < grid.length; i++) {
            for (int left = 0; left < grid[0].length; left++) {
                if (grid[i][left] == 0) continue;
                for (int right = left + 1; right < grid[0].length; right++) {
                    if (grid[i][right] == 0) continue;
                    if (grid[i][left] > grid[i][right]) {
                        int goalRowForLeft = (grid[i][left] - 1) / n;
                        int goalRowForRight = (grid[i][right] - 1) / n;
                        if (goalRowForLeft == goalRowForRight && goalRowForRight == i) {
                            conflicts++;
                        }
                    }
                }
            }
        }
        // count the conflicts in columns
        for (int j = 0; j < grid[0].length; j++) {
            for (int up = 0; up < grid.length; up++) {
                if (grid[up][j] == 0) continue;
                for (int down = up + 1; down < grid.length; down++) {
                    if (grid[down][j] == 0) continue;
                    if (grid[up][j] > grid[down][j]) {
                        int goalColForUp = (grid[up][j] - 1) % n;
                        int goalColForDown = (grid[down][j] - 1) % n;
                        if (goalColForUp == goalColForDown && goalColForDown == j) {
                            conflicts++;
                        }
                    }
                }
            }
        }
        return manhattan + 2 * conflicts;
    }



}
