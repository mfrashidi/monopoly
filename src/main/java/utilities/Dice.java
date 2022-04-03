package utilities;

import java.io.IOException;
import java.lang.Math;

public class Dice {
    public static int roll(boolean test) throws IOException {
        if (test) return System.in.read() - 48;
        else return (int)(Math.random()*(6)+1);
    }
}
