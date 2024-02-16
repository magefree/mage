package mage.constants;

/**
 * @author North
 */
public enum SpellAbilityType {
    BASE("Basic SpellAbility"),
    BASE_ALTERNATE("Basic SpellAbility Alternate"), // used for Overload, Flashback to know they must be handled as Alternate casting costs
    SPLIT("Split SpellAbility"),
    SPLIT_AFTERMATH("AftermathSplit SpellAbility"),
    SPLIT_FUSED("Split SpellAbility"),
    SPLIT_LEFT("LeftSplit SpellAbility"),
    SPLIT_RIGHT("RightSplit SpellAbility"),
    MODAL("Modal SpellAbility"), // used for modal double faces cards
    MODAL_LEFT("LeftModal SpellAbility"),
    MODAL_RIGHT("RightModal SpellAbility"),
    SPLICE("Spliced SpellAbility"),
    ADVENTURE_SPELL("Adventure SpellAbility");

    private final String text;

    SpellAbilityType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
