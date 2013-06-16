package mage.constants;

/**
 *
 * @author North
 */
public enum TurnPhase {
    BEGINNING ("Beginning"),
    PRECOMBAT_MAIN ("Precombat Main"),
    COMBAT ("Combat"),
    POSTCOMBAT_MAIN ("Postcombat Main"),
    END ("End");

    private String text;

    TurnPhase(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
