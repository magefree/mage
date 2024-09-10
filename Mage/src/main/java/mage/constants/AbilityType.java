package mage.constants;

/**
 * @author North, Susucr
 */
public enum AbilityType {
    PLAY_LAND("Play land", true, false, false, false),
    SPELL("Spell", true, false, false, false),
    STATIC("Static", false, false, false, false),
    EVASION("Evasion", false, false, false, false),
    ACTIVATED_NONMANA("Activated", false, true, false, false),
    ACTIVATED_MANA("Mana", false, true, false, true),
    TRIGGERED_NONMANA("Triggered", false, false, true, false),
    TRIGGERED_MANA("Triggered Mana", false, false, true, true),
    SPECIAL_ACTION("Special Action", false, false, false, false),
    SPECIAL_MANA_PAYMENT("Special Mana Payment", false, false, false, false); // No activated ability and no special action. (e.g. Improvise, Delve)

    private final String text;
    private final boolean playCardAbility;
    private final boolean activatedAbility;
    private final boolean triggeredAbility;
    private final boolean manaAbility;

    AbilityType(String text, boolean playCardAbility, boolean activatedAbility, boolean triggeredAbility, boolean manaAbility) {
        this.text = text;
        this.playCardAbility = playCardAbility;
        this.activatedAbility = activatedAbility;
        this.triggeredAbility = triggeredAbility;
        this.manaAbility = manaAbility;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isPlayCardAbility() {
        return playCardAbility;
    }

    public boolean isActivatedAbility() {
        return activatedAbility;
    }

    public boolean isTriggeredAbility() {
        return triggeredAbility;
    }

    public boolean isNonManaActivatedAbility() {
        return activatedAbility && !manaAbility;
    }

    public boolean isManaActivatedAbility() {
        return activatedAbility && manaAbility;
    }

    public boolean isManaAbility() {
        return manaAbility;
    }
}
