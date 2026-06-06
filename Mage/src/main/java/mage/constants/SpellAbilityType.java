package mage.constants;

/**
 * @author North
 */
public enum SpellAbilityType {
    BASE("Basic SpellAbility"),
    BASE_ALTERNATE("Basic SpellAbility Alternate"), // used for Overload, Flashback to know they must be handled as Alternate casting costs
    SPLIT("Split SpellAbility", false),
    SPLIT_AFTERMATH("AftermathSplit SpellAbility"),
    SPLIT_FUSED("Split SpellAbility"),
    SPLIT_LEFT("LeftSplit SpellAbility"),
    SPLIT_RIGHT("RightSplit SpellAbility"),
    TRANSFORMED("Transformed SpellAbility", false),
    TRANSFORMED_LEFT("TransformFront SpellAbility"),
    TRANSFORMED_RIGHT("TransformBack SpellAbility", false),
    MODAL("Modal SpellAbility", false), // used for modal double faces cards
    MODAL_LEFT("LeftModal SpellAbility"),
    MODAL_RIGHT("RightModal SpellAbility"),
    SPLICE("Spliced SpellAbility"),
    ADVENTURE_OMEN("Adventure/Omen SpellAbility", false),
    ADVENTURE_OMEN_LEFT("Adventure/Omen Left SpellAbility"),
    ADVENTURE_OMEN_RIGHT("Adventure/Omen Right SpellAbility"),
    MELD("Meld SpellAbility", false),
    MELD_LEFT("MeldLeft SpellAbility"),
    MELD_RIGHT("MeldRight SpellAbility", false);

    private final String text;
    private final boolean canCast;

    SpellAbilityType(String text) {
        this.text = text;
        this.canCast = true;
    }

    SpellAbilityType(String text, boolean canCast) {
        this.text = text;
        this.canCast = canCast;
    }

    public boolean canCast() {
        return canCast;
    }

    @Override
    public String toString() {
        return text;
    }
}
