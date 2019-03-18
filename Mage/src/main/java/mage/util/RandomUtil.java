package mage.util;

import java.util.Random;

/**
 * Created by IGOUDT on 5-9-2016.
 */
public final class RandomUtil {

    private static Random random = new Random(); // thread safe with seed support

    private RandomUtil() {
    }

    public static Random getRandom() {
        return random;
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(int max) {
        return random.nextInt(max);
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static void setSeed(long newSeed) {
        random.setSeed(newSeed);
    }
}
