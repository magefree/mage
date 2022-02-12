package mage.abilities.icon.abilities;

import mage.abilities.icon.CardIcon;
import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;
import mage.util.CardUtil;

/**
 * @author JayDi85
 */
public enum HexproofAbilityIcon implements CardIcon {
    instance;

    @Override
    public CardIconType getIconType() {
        return CardIconType.ABILITY_HEXPROOF;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getHint() {
        return "Hexproof ability";
    }

    @Override
    public CardIcon copy() {
        return instance;
    }

    public static CardIconImpl createDynamicCardIcon(String hint) {
        return new CardIconImpl(CardIconType.ABILITY_HEXPROOF, CardUtil.getTextWithFirstCharUpperCase(hint));
    }
}
