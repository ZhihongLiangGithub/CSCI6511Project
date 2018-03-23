package project3;

import com.google.common.base.Splitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class NPuzzle {
    int[][] grid;

    public NPuzzle() {

    }

    public void solve() {

    }

    public void generateNGrid(int n) {
        int[][] g = new int[n][n];
        List<Integer> nums = new ArrayList<>();
        int max = n * n;
        for (int i = 1; i < max; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);
        nums.add(0);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                g[i][j] = nums.get(i * n + j);
            }
        }
        grid = g.clone();
        printGrid();
    }

    public void readGridFromFile(String filePath) {
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
                grid = g.clone();
                printGrid();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printGrid() {
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
