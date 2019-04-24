package mage.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by IGOUDT on 5-9-2016.
 */
public final class RandomUtil {

    private RandomUtil() {
    }

    public static Random getRandom() {
        return ThreadLocalRandom.current();
    }

    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int nextInt(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
