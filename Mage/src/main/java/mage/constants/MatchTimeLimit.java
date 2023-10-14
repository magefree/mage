package mage.constants;

/**
 * The time per player to have activity in a match.
 * If time runs out for a player, they loose the currently running game of a match.
 *
 * @author LevelX2
 */
public enum MatchTimeLimit {
    NONE(0,"None"),
    MIN__10(600, "10 Minutes"),
    MIN__15(900, "15 Minutes"),
    MIN__20(1200, "20 Minutes"),
    MIN__25(1500, "25 Minutes"),
    MIN__30(1800, "30 Minutes"),
    MIN__35(2100, "35 Minutes"),
    MIN__40(2400, "40 Minutes"),
    MIN__45(2700, "45 Minutes"),
    MIN__50(3000, "50 Minutes"),
    MIN__55(3300, "55 Minutes"),
    MIN__60(3600, "60 Minutes"),
    MIN__90(5400, "90 Minutes"),
    MIN_120(7200, "120 Minutes");

    private final int prioritySecs;
    private final String name;

    MatchTimeLimit(int prioritySecs, String name) {
        this.prioritySecs = prioritySecs;
        this.name = name;
    }

    public int getPrioritySecs() {
        return prioritySecs;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
