package mage.abilities.icon.abilities;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum FirstStrikeAbilityIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.ABILITY_FIRST_STRIKE;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "First strike ability";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
