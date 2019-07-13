package mage.utils;

import mage.util.JarVersion;

import java.io.Serializable;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersion implements Serializable, Comparable<MageVersion> {

    public static final int MAGE_VERSION_MAJOR = 1;
    public static final int MAGE_VERSION_MINOR = 4;
    public static final int MAGE_VERSION_PATCH = 37;
    public static final String MAGE_EDITION_INFO = ""; // set "-beta" for 1.4.32-betaV0
    public static final String MAGE_VERSION_MINOR_PATCH = "V4"; // default
    // strict mode
    private static final boolean MAGE_VERSION_MINOR_PATCH_MUST_BE_SAME = true; // set true on uncompatible github changes, set false after new major release (after MAGE_VERSION_PATCH changes)

    public static final boolean MAGE_VERSION_SHOW_BUILD_TIME = true;
    private final int major;
    private final int minor;
    private final int patch;
    private final String minorPatch; // doesn't matter for compatibility
    private final String buildTime;
    private String editionInfo;

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
        this.buildTime = JarVersion.getBuildTime(sourceClass);
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

    public String toString(boolean showBuildTime) {
        // 1.4.32-betaV0 (build: time)
        String res = major + "." + minor + '.' + patch + editionInfo + minorPatch;
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
        if (patch != o.patch) {
            return patch - o.patch;
        }
        if (MAGE_VERSION_MINOR_PATCH_MUST_BE_SAME && !minorPatch.equals(o.minorPatch)) {
            return minorPatch.compareTo(o.minorPatch);
        }
        return editionInfo.compareTo(o.editionInfo);
    }
}
