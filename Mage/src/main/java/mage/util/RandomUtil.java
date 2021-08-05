package mage.util;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;

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

    public static Color nextColor() {
        return new Color(RandomUtil.nextInt(256), RandomUtil.nextInt(256), RandomUtil.nextInt(256));
    }

    public static void setSeed(long newSeed) {
        random.setSeed(newSeed);
    }

    public static <T extends Serializable> T randomFromSet(Set<T> collection) {
        if (collection.size() < 2) {
            return collection.stream().findFirst().orElse(null);
        }
        int rand = nextInt(collection.size());
        int count = 0;
        for (T currentId : collection) {
            if (count == rand) {
                return currentId;
            }
            count++;
        }
        return null;
    }
}
