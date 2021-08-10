package mage.abilities.costs;

/**
 * See rules 601.2b
 *
 * @author JayDi85
 */
public enum VariableCostType {

    NORMAL(false),
    ALTERNATIVE(false),
    ADDITIONAL(true);

    // allows announcing X value on free cast (noMana) for additional costs, example: Kicker X
    private final boolean canUseAnnounceOnFreeCast;

    VariableCostType(boolean canUseAnnounceOnFreeCast) {
        this.canUseAnnounceOnFreeCast = canUseAnnounceOnFreeCast;
    }

    public boolean canUseAnnounceOnFreeCast() {
        return this.canUseAnnounceOnFreeCast;
    }
}
