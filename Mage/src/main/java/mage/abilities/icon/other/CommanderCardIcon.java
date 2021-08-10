package mage.abilities.icon.other;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum CommanderCardIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.COMMANDER;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Card is commander";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
