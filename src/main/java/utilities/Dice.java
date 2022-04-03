package utilities;

import java.io.IOException;
import java.lang.Math;

public class Dice {
    public static int roll(boolean test) throws IOException {
        if (test){
            int input = -1;
            while (input > 6 || input < 0){
                input = System.in.read() - 48;
            }
            return input;
        }
        else return (int)(Math.random()*(6)+1);
    }
}
