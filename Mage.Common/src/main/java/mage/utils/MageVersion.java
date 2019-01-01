package mage.utils;

import mage.util.JarVersion;

import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersion implements Serializable, Comparable<MageVersion> {

    /**
     *
     */
    public final static int MAGE_VERSION_MAJOR = 1;
    public final static int MAGE_VERSION_MINOR = 4;
    public final static int MAGE_VERSION_PATCH = 32;
    public final static String MAGE_EDITION_INFO = ""; // set "-beta" for 1.4.32-betaV0
    public final static String MAGE_VERSION_MINOR_PATCH = "V0";

    private final int major;
    private final int minor;
    private final int patch;
    private final String minorPatch; // doesn't matter for compatibility
    private final String buildTime;
    private String editionInfo;
    private final boolean showBuildTime = true;

    public MageVersion(Class sourceClass) {
        this(MAGE_VERSION_MAJOR, MAGE_VERSION_MINOR, MAGE_VERSION_PATCH, MAGE_VERSION_MINOR_PATCH, MAGE_EDITION_INFO, sourceClass);
    }

    public MageVersion(int major, int minor, int patch, String minorPatch, String editionInfo, Class sourceClass) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.minorPatch = minorPatch;
        this.editionInfo = editionInfo;

        // build time
        this.buildTime = showBuildTime ? JarVersion.getBuildTime(sourceClass) : "";
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getMinorPatch() {
        return minorPatch;
    }

    @Override
    public String toString() {
        // 1.4.32-betaV0 (build: time)
        return major + "." + minor + '.' + patch + editionInfo + minorPatch + (!this.buildTime.isEmpty() ? " (build: " + this.buildTime + ")" : "");
    }

    @Override
    public int compareTo(MageVersion o) {
        if (major != o.major) {
            return major - o.major;
        }
        if (minor != o.minor) {
            return minor - o.minor;
        }
        if (patch != o.patch) {
            return patch - o.patch;
        }
        return editionInfo.compareTo(o.editionInfo);
    }

}
