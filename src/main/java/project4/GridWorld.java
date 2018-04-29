package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class GridWorld {

    private Double[][] grid;
    private List<int[]> possibleStates;
    private List<Action> possibleActions;
    private double discount;
    private double livingReward;
    private double noiseForward;
    private double noiseLeftward;
    private double noiseRightward;
    private double noiseBackward;


    public GridWorld(double discount, double livingReward, int option) {
        this.possibleStates = new ArrayList<>();
        this.possibleActions = Arrays.asList(Action.NORTH, Action.WEST, Action.EAST, Action.SOUTH);
        this.discount = discount;
        this.livingReward = livingReward;
        this.readGrid("src/main/resources/project4/gridA." + option + ".csv");
        double[][] noiseConfig = new double[][]{{0.65, 0.15, 0.15, 0.05}, {0.6, 0.1, 0.15, 0.15}}; // forward, leftward, rightward, backward
        this.noiseForward = noiseConfig[option - 1][0];
        this.noiseLeftward = noiseConfig[option - 1][1];
        this.noiseRightward = noiseConfig[option - 1][2];
        this.noiseBackward = noiseConfig[option - 1][3];
    }

    public void getVstar(int iteration) {
        for (int iter = 0; iter < iteration; iter++) {
            List<Double> newValue = new ArrayList<>();
            for (int[] state : possibleStates) {
                List<Double> qValues = new ArrayList<>();
                for (Action action : possibleActions) {
                    qValues.add(getQValue(state, action));
                }
                Collections.sort(qValues);
                double maxQ = qValues.get(qValues.size() - 1);
                newValue.add(maxQ);
            }
            // update the grid
            for (int k = 0; k < possibleStates.size(); k++) {
                int i = possibleStates.get(k)[0];
                int j = possibleStates.get(k)[1];
                grid[i][j] = newValue.get(k);
            }
        }
        System.out.println("V* after " + iteration + " iteration(s): ");
        printGrid(grid);
    }

    private double getQValue(int[] state, Action action) {
        int i = state[0];
        int j = state[1];
        int[] forwardState = null;
        int[] leftwardState = null;
        int[] rightwardState = null;
        int[] backwardState = null;
        if (action == Action.NORTH) {
            forwardState = i > 0 && grid[i - 1][j] != null ? new int[]{i - 1, j} : state;
            leftwardState = j > 0 && grid[i][j - 1] != null ? new int[]{i, j - 1} : state;
            rightwardState = j < grid[0].length - 1 && grid[i][j + 1] != null ? new int[]{i, j + 1} : state;
            backwardState = i < grid.length - 1 && grid[i + 1][j] != null ? new int[]{i + 1, j} : state;
        } else if (action == Action.WEST) {
            forwardState = j > 0 && grid[i][j - 1] != null ? new int[]{i, j - 1} : state;
            leftwardState = i < grid.length - 1 && grid[i + 1][j] != null ? new int[]{i + 1, j} : state;
            rightwardState = i > 0 && grid[i - 1][j] != null ? new int[]{i - 1, j} : state;
            backwardState = j < grid[0].length - 1 && grid[i][j + 1] != null ? new int[]{i, j + 1} : state;
        } else if (action == Action.EAST) {
            forwardState = j < grid[0].length - 1 && grid[i][j + 1] != null ? new int[]{i, j + 1} : state;
            leftwardState = i > 0 && grid[i - 1][j] != null ? new int[]{i - 1, j} : state;
            rightwardState = i < grid.length - 1 && grid[i + 1][j] != null ? new int[]{i + 1, j} : state;
            backwardState = j > 0 && grid[i][j - 1] != null ? new int[]{i, j - 1} : state;
        } else if (action == Action.SOUTH) {
            forwardState = i < grid.length - 1 && grid[i + 1][j] != null ? new int[]{i + 1, j} : state;
            leftwardState = j < grid[0].length - 1 && grid[i][j + 1] != null ? new int[]{i, j + 1} : state;
            rightwardState = j > 0 && grid[i][j - 1] != null ? new int[]{i, j - 1} : state;
            backwardState = i > 0 && grid[i - 1][j] != null ? new int[]{i - 1, j} : state;
        }
        double qValue = noiseForward * (livingReward + discount * grid[forwardState[0]][forwardState[1]])
                + noiseLeftward * (livingReward + discount * grid[leftwardState[0]][leftwardState[1]])
                + noiseRightward * (livingReward + discount * grid[rightwardState[0]][rightwardState[1]])
                + noiseBackward * (livingReward + discount * grid[backwardState[0]][backwardState[1]]);
        return qValue;
    }


    private void readGrid(String filePath) {
        File file = new File(filePath);
        List<List<Double>> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                List<Double> row = Arrays.stream(s.split(","))
                        .map(a -> {
                            if (a.equals("-")) return null;
                            else return Double.valueOf(a);
                        }).collect(Collectors.toList());
                list.add(row);
            }
            if (list.size() != 0) {
                Double[][] g = new Double[list.size()][];
                for (int i = 0; i < list.size(); i++) {
                    List<Double> row = list.get(i);
                    g[i] = new Double[row.size()];
                    for (int j = 0; j < row.size(); j++) {
                        g[i][j] = row.get(j);
                        if (g[i][j] != null && g[i][j] == 0.0) {
                            possibleStates.add(new int[]{i, j});
                        }
                    }
                }
                System.out.println("Read grid from file:");
                System.out.println(filePath);
                printGrid(g);
                grid = g;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printGrid(Double[][] grid) {
        if (grid == null || grid.length == 0) return;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == null) {
                    System.out.printf("%8s", "-");
                } else {
                    System.out.printf("%8.2f", grid[i][j]);
                }
            }
            System.out.println();
        }
    }

    enum Action {
        NORTH, WEST, EAST, SOUTH
    }
}
