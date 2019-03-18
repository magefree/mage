
package mage.constants;

/**
 *
 * @author spjspj
 */

public enum PlanarDieRoll {
    NIL_ROLL ("Nil Roll"),
    CHAOS_ROLL("Chaos Roll"),
    PLANAR_ROLL ("Planar Roll");

    private final String text;

    PlanarDieRoll(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
