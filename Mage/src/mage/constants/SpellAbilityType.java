package mage.constants;

/**
 *
 * @author North
 */
public enum SpellAbilityType {
    BASE("Basic SpellAbility"),
    SPLIT("Split SpellAbility"),
    SPLIT_FUSED("Split SpellAbility"),
    SPLIT_LEFT("LeftSplit SpellAbility"),
    SPLIT_RIGHT("RightSplit SpellAbility"),
    MODE("Mode SpellAbility"),
    SPLICE("Spliced SpellAbility");

    private String text;

    SpellAbilityType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
