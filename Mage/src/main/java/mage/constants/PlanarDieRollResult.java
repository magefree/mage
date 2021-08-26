package mage.constants;

/**
 *
 * @author spjspj
 */
public enum PlanarDieRollResult {

    BLANK_ROLL("Blank Roll", 0),
    CHAOS_ROLL("Chaos Roll", 2),
    PLANAR_ROLL("Planar Roll", 1);

    private final String text;
    private final int aiPriority; // priority for AI usage (0 - lower, 2 - higher)

    PlanarDieRollResult(String text, int aiPriority) {
        this.text = text;
        this.aiPriority = aiPriority;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getAIPriority() {
        return aiPriority;
    }
}
