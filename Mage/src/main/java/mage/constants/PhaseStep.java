package mage.constants;

/**
 * @author North
 */
public enum PhaseStep {
    UNTAP("Untap", 0, "untap step", "UN"),
    UPKEEP("Upkeep", 1, "upkeep", "UP"), // card texts don't use the word "step" for this phase step
    DRAW("Draw", 2, "draw step", "DR"),
    PRECOMBAT_MAIN("Precombat Main", 3, "precombat main step", "M1"),
    BEGIN_COMBAT("Begin Combat", 4, "begin combat step", "BC"),
    DECLARE_ATTACKERS("Declare Attackers", 5, "declare attackers step", "DA"),
    DECLARE_BLOCKERS("Declare Blockers", 6, "declare blockers step", "DB"),
    FIRST_COMBAT_DAMAGE("First Combat Damage", 7, "first combat damage", "FCD"),
    COMBAT_DAMAGE("Combat Damage", 8, "combat damage step", "CD"),
    END_COMBAT("End Combat", 9, "end of combat step", "EC"),
    POSTCOMBAT_MAIN("Postcombat Main", 10, "postcombat main step", "M2"),
    END_TURN("End Turn", 11, "end turn step", "ET"),
    CLEANUP("Cleanup", 12, "cleanup step", "CL");

    private final String text;
    private final String stepText;
    private final String stepShortText; // for chats/logs

    /**
     * Index is used for game state scoring system.
     */
    private final int index;

    PhaseStep(String text, int index, String stepText, String stepShortText) {
        this.text = text;
        this.index = index;
        this.stepText = stepText;
        this.stepShortText = stepShortText;
    }

    public boolean isBefore(PhaseStep other) {
        return this.getIndex() < other.getIndex();
    }

    public boolean isAfter(PhaseStep other) {
        return this.getIndex() > other.getIndex();
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

    public String getStepShortText() {
        return stepShortText;
    }
}
