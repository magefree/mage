package mage.abilities.icon.abilities;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum DoubleStrikeAbilityIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.ABILITY_DOUBLE_STRIKE;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Double strike ability";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
