package mage.abilities.icon.abilities;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum FlyingAbilityIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.ABILITY_FLYING;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Flying ability";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
