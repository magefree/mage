package mage.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by IGOUDT on 5-9-2016.
 */
public class RandomUtil {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static Random getRandom(){
        return random;
    }

    public static int nextInt(){
        return random.nextInt();
    }

    public static int nextInt(int max){
        return random.nextInt(max);
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
