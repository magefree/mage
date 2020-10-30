package mage.constants;

/**
 * @author North
 */
public enum TurnPhase {
    BEGINNING("Beginning"),
    PRECOMBAT_MAIN("Precombat Main"),
    COMBAT("Combat"),
    POSTCOMBAT_MAIN("Postcombat Main"),
    END("End");

    private final String text;

    TurnPhase(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isMain() {
        return this == PRECOMBAT_MAIN || this == POSTCOMBAT_MAIN;
    }
}
