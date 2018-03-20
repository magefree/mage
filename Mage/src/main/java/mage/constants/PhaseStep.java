package mage.constants;

/**
 *
 * @author North
 */
public enum PhaseStep {
    UNTAP ("Untap", 0, "untap step"),
    UPKEEP ("Upkeep", 1, "upkeep"), // card texts don't use the word "step" for this phase step
    DRAW ("Draw", 2, "draw step"),
    PRECOMBAT_MAIN ("Precombat Main", 3,"precombat main step"),
    BEGIN_COMBAT ("Begin Combat", 4, "begin combat step"),
    DECLARE_ATTACKERS ("Declare Attackers", 5, "declare attackers step"),
    DECLARE_BLOCKERS ("Declare Blockers", 6, "declare blockers step"),
    FIRST_COMBAT_DAMAGE ("First Combat Damage", 7, "first combat damage"),
    COMBAT_DAMAGE ("Combat Damage", 8, "combat damage step"),
    END_COMBAT ("End Combat", 9, "end combat step"),
    POSTCOMBAT_MAIN ("Postcombat Main", 10, "postcombat main step"),
    END_TURN ("End Turn", 11, "end turn step"),
    CLEANUP ("Cleanup", 12, "cleanup step");

    private final String text;
    private final String stepText;

    /**
     * Index is used for game state scoring system.
     */
    private final int index;

    PhaseStep(String text, int index, String stepText) {
        this.text = text;
        this.index = index;
        this.stepText = stepText;
    }

    public boolean isBefore(PhaseStep other){
        return this.getIndex()<other.getIndex();
    }

    public boolean isAfter(PhaseStep other){
        return this.getIndex()>other.getIndex();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getStepText() {
        return stepText;
    }

}
