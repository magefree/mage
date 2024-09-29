package mage.constants;

/**
 * The time per player to have activity in a match.
 * If time runs out for a player, they lose the match.
 *
 * @author LevelX2
 */
public enum MatchTimeLimit {
    NONE(0),
    MIN___5(300),
    MIN__10(600),
    MIN__15(900),
    MIN__20(1200),
    MIN__25(1500),
    MIN__30(1800),
    MIN__35(2100),
    MIN__40(2400),
    MIN__45(2700),
    MIN__50(3000),
    MIN__55(3300),
    MIN__60(3600),
    MIN__90(5400),
    MIN_120(7200);

    private final int prioritySecs;

    MatchTimeLimit(int prioritySecs) {
        this.prioritySecs = prioritySecs;
    }

    public int getPrioritySecs() {
        return prioritySecs;
    }

    public String getName() {
        if (this == NONE) {
            return "None";
        } else {
            return (prioritySecs/60) + " Minutes";
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getShortName() {
        if (this == NONE) {
            return "None";
        } else {
            return (prioritySecs/60) + "m";
        }
    }
}
