package mage.client.util.stats;

/**
 * @author noxx
 */
public final class MemoryUsageStatUtil {

    private MemoryUsageStatUtil() {}

    /**
     * Returns percentage of available memory used at runtime.
     * If not possible to determine, returns -1.
     *
     * @return
     */
    public static float getMemoryFreeStatPercentage() {
        Runtime runtime = Runtime.getRuntime();
        if (runtime.maxMemory() != 0) {
            long usedMem = runtime.totalMemory() - runtime.freeMemory();
            return (1 - (1.0f*usedMem)/runtime.maxMemory())*100;
        } else {
            return -1;
        }
    }
}
