package mage.constants;

/**
 *
 * @author North
 */
public enum Duration {
    OneUse("", true),
    EndOfGame("for the rest of the game", false),
    WhileOnBattlefield("", false),
    WhileOnStack("", false),
    WhileInGraveyard("", false),
    EndOfTurn("until end of turn", true),
    UntilYourNextTurn("until your next turn", true),
    EndOfCombat("until end of combat", true),
    EndOfStep("until end of phase step", true),
    Custom("", true);

    private final String text;
    private final boolean onlyValidIfNoZoneChange; // defines if an effect lasts only if the source has not chnaged zone since init of the effect

    Duration(String text, boolean onlyValidIfNoZoneChange) {
        this.text = text;
        this.onlyValidIfNoZoneChange = onlyValidIfNoZoneChange;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isOnlyValidIfNoZoneChange() {
        return onlyValidIfNoZoneChange;
    }

}
