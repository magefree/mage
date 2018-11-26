package mage.utils;

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MageVersion implements Serializable, Comparable<MageVersion> {

    /**
     *
     */
    public final static int MAGE_VERSION_MAJOR = 1;
    public final static int MAGE_VERSION_MINOR = 4;
    public final static int MAGE_VERSION_PATCH = 31;
    public final static String MAGE_VERSION_MINOR_PATCH = "V4";
    public final static String MAGE_VERSION_INFO = "";

    private final int major;
    private final int minor;
    private final int patch;
    private final String minorPatch; // doesn't matter for compatibility

    private String info = "";

    public MageVersion(int major, int minor, int patch, String minorPatch, String info) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.minorPatch = minorPatch;
        this.info = info;
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
        return major + "." + minor + '.' + patch + info + minorPatch;
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
        return info.compareTo(o.info);
    }

}
