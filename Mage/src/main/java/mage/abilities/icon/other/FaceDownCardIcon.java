package mage.abilities.icon.other;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconType;

/**
 * @author JayDi85
 */
public enum FaceDownCardIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.OTHER_FACEDOWN;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Card is face down";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }
}
