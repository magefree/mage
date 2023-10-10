package mage.utils;

import mage.util.JarVersion;

import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersion implements Serializable, Comparable<MageVersion> {

    // version must be compatible with maven version numbers
    // launcher can update only to the newest version
    // example: 1.4.48-V1-beta3
    // * 1.4.48 compares as numbers
    // * V1-beta3 is qualifier and compares as string
    // * launcher gives priority to 1.4.48 instead 1.4.48-any-text, so don't use empty release info
    public static final int MAGE_VERSION_MAJOR = 1;
    public static final int MAGE_VERSION_MINOR = 4;
    public static final int MAGE_VERSION_RELEASE = 52;
    public static final String MAGE_VERSION_RELEASE_INFO = "V4-beta4"; // V1 for releases, V1-beta3 for betas

    // strict mode
    // Each update requires a strict version
    // If you disable it then server can accept multiple versions with same release number
    // Since incompatible changes are no longer monitored - it must always be true
    private static final boolean MAGE_VERSION_RELEASE_INFO_MUST_BE_SAME = true;
    // build info
    public static final boolean MAGE_VERSION_SHOW_BUILD_TIME = true;

    private final int major;
    private final int minor;
    private final int release;
    private final String releaseInfo;
    private final String buildTime;

    public MageVersion(Class sourceClass) {
        this(MAGE_VERSION_MAJOR, MAGE_VERSION_MINOR, MAGE_VERSION_RELEASE, MAGE_VERSION_RELEASE_INFO, sourceClass);
    }

    public MageVersion(int major, int minor, int release, String releaseInfo, Class sourceClass) {
        this.major = major;
        this.minor = minor;
        this.release = release;
        this.releaseInfo = releaseInfo;

        if (!releaseInfo.startsWith("V")) {
            // release: V1
            // beta: V1-beta3
            throw new IllegalArgumentException("ERROR, release info must be started from V.");
        }

        // build time
        this.buildTime = JarVersion.getBuildTime(sourceClass);
    }

    public String toString(boolean showBuildTime) {
        // 1.4.32-V1-beta2 (build: time)
        String res = major + "." + minor + '.' + release + "-" + releaseInfo;
        if (showBuildTime && !this.buildTime.isEmpty()) {
            res += " (build: " + this.buildTime + ")";
        }
        return res;
    }

    @Override
    public String toString() {
        return toString(MAGE_VERSION_SHOW_BUILD_TIME);
    }

    @Override
    public int compareTo(MageVersion o) {
        if (major != o.major) {
            return major - o.major;
        }
        if (minor != o.minor) {
            return minor - o.minor;
        }
        if (release != o.release) {
            return release - o.release;
        }

        if (MAGE_VERSION_RELEASE_INFO_MUST_BE_SAME && !releaseInfo.equals(o.releaseInfo)) {
            return releaseInfo.compareTo(o.releaseInfo);
        }

        // all fine
        return 0;
    }

    public boolean isDeveloperBuild() {
        return this.buildTime.contains(JarVersion.JAR_BUILD_TIME_FROM_CLASSES);
    }
}
