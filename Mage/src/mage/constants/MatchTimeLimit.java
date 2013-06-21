package mage.constants;

/**
 *
 * @author LevelX2
 */
public enum MatchTimeLimit {
    NONE(0,"None"),
    MIN__20(1200, "20 Minutes"),
    MIN__30(1800, "30 Minutes"),
    MIN__40(2400, "40 Minutes"),
    MIN__50(3000, "50 Minutes"),
    MIN__60(3600, "60 Minutes"),
    MIN__90(5400, "90 Minutes"),
    MIN_120(7200, "120 Minutes");

    private int matchSeconds;
    private String name;

    MatchTimeLimit(int matchSeconds, String name) {
        this.matchSeconds = matchSeconds;
        this.name = name;
    }

    public int getTimeLimit() {
        return matchSeconds;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
