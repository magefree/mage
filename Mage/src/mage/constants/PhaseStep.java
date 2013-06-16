package mage.constants;

/**
 *
 * @author North
 */
public enum PhaseStep {
    UNTAP ("Untap", 0),
    UPKEEP ("Upkeep", 1),
    DRAW ("Draw", 2),
    PRECOMBAT_MAIN ("Precombat Main", 3),
    BEGIN_COMBAT ("Begin Combat", 4),
    DECLARE_ATTACKERS ("Declare Attackers", 5),
    DECLARE_BLOCKERS ("Declare Blockers", 6),
    FIRST_COMBAT_DAMAGE ("First Combat Damage", 7),
    COMBAT_DAMAGE ("Combat Damage", 8),
    END_COMBAT ("End Combat", 9),
    POSTCOMBAT_MAIN ("Postcombat Main", 10),
    END_TURN ("End Turn", 11),
    CLEANUP ("Cleanup", 12);

    private String text;

    /**
     * Index is used for game state scoring system.
     */
    private int index;

    PhaseStep(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return text;
    }

}
