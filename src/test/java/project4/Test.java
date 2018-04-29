package project4;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        // project A
        //GridWorld gw = new GridWorld(0.9, -0.01, 1);
        //gw.getVstar(100);
        // project B
        Dealer dl = new Dealer();
        File file = new File("src/main/resources/project4/diceSequence.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                dl.evaluate(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
