package mage.constants;

/**
 * @author North
 */
public enum Duration {
    OneUse("", true, true),
    EndOfGame("for the rest of the game", false, false),
    WhileOnBattlefield("", false, false),
    WhileControlled("for as long as you control {this}", true, false),
    WhileOnStack("", false, true),
    WhileInGraveyard("", false, false),
    EndOfTurn("until end of turn", true, true),
    UntilYourNextTurn("until your next turn", true, true),
    UntilYourNextEndStep("until your next end step", true, true),
    UntilEndCombatOfYourNextTurn("until end of combat on your next turn", true, true),
    UntilYourNextUpkeepStep("until your next upkeep", true, true),
    UntilEndOfYourNextTurn("until the end of your next turn", true, true),
    UntilSourceLeavesBattlefield("until {this} leaves the battlefield", true, false), // supported for continuous layered effects
    EndOfCombat("until end of combat", true, true),
    EndOfStep("until end of phase step", true, true),
    Custom("", true, true);

    private final String text;
    private final boolean onlyValidIfNoZoneChange; // defines if an effect lasts only if the source has not changed zone since init of the effect
    private final boolean fixedController; // has the controller of the effect to change, if the controller of the source changes

    Duration(String text, boolean onlyValidIfNoZoneChange, boolean fixedController) {
        this.text = text;
        this.onlyValidIfNoZoneChange = onlyValidIfNoZoneChange;
        this.fixedController = fixedController;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isOnlyValidIfNoZoneChange() {
        return onlyValidIfNoZoneChange;
    }

    public boolean isFixedController() {
        return fixedController;
    }
}
