package mage.constants;

/**
 *
 * @author spjspj
 */
public enum PlanarDieRollResult {

    BLANK_ROLL("Blank Roll"),
    CHAOS_ROLL("Chaos Roll"),
    PLANAR_ROLL("Planar Roll");

    private final String text;

    PlanarDieRollResult(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
