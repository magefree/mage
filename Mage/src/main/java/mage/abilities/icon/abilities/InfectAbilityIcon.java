package mage.abilities.icon.abilities;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum InfectAbilityIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.ABILITY_INFECT;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Infect ability";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
