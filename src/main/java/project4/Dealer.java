package project4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dealer {

    private double[] initial;
    private double[][] transition;
    private double[][] emission;

    public Dealer() {
        // initial probability
        this.initial = new double[]{1.0 / 3, 1.0 / 3, 1.0 / 3};
        // transition probability, hidden states: D1, D2, D3
        this.transition = new double[][]{
                {2.0 / 3, 1.0 / 6, 1.0 / 6},
                {1.0 / 6, 2.0 / 3, 1.0 / 6},
                {1.0 / 6, 1.0 / 6, 2.0 / 3}};
        // emission probability
        this.emission = new double[][]{
                {0.6, 0.2, 0.2},
                {0.2, 0.6, 0.2},
                {0.2, 0.2, 0.6}
        };
    }


    /**
     * guess the most likely sequence of hidden states using Viterbi algorithm
     *
     * @param diceSequence sequence of dice outputs
     * @return sequence of dice that was used
     */
    public int[] evaluate(String diceSequence) {
        if (diceSequence == null || diceSequence.length() == 0) return null;
        System.out.println("Sequence of dice outputs: " + diceSequence);
        List<Integer> observation = Arrays.stream(diceSequence.split(","))
                .map(o -> Integer.valueOf(o.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());
        // the probability of the most likely path so far
        double[][] T1 = new double[transition.length][observation.size()];
        // the most likely previous state so far
        int[][] T2 = new int[transition.length][observation.size()];
        // initial
        for (int state = 0; state < transition.length; state++) {
            T1[state][0] = initial[0] * emission[state][observation.get(0) - 1];
            T2[state][0] = -1;
        }
        for (int t = 1; t < observation.size(); t++) {
            int ob = observation.get(t);
            for (int y = 0; y < transition.length; y++) {
                // find the max and argmax
                double max = -1;
                int argmax = -1;
                for (int k = 0; k < transition.length; k++) {
                    double prob = T1[k][t - 1] * transition[k][y] * emission[y][ob - 1];
                    if (prob > max) {
                        max = prob;
                        argmax = k;
                    }
                }
                T1[y][t] = max;
                T2[y][t] = argmax;
            }
        }
        // the highest probability
        double maxProb = -1;
        int z = -1;
        for (int k = 0; k < transition.length; k++) {
            if (T1[k][observation.size() - 1] > maxProb) {
                maxProb = T1[k][observation.size() - 1];
                z = k;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(z + 1);
        // follow the backtrack till the first observation
        for (int i = observation.size() - 1; i >= 1; i--) {
            z = T2[z][i];
            builder.append(z + 1);
        }
        String seq = builder.reverse().toString();
        System.out.println("The Most likely sequence of dice: " + seq + ", probability: " + maxProb);
        int[] res = new int[seq.length()];
        for (int i = 0; i < seq.length(); i++) {
            res[i] = seq.charAt(i) - '0';
        }
        return res;
    }
}
