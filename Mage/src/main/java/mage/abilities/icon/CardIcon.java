package mage.abilities.icon;

import mage.util.Copyable;

/**
 * @author JayDi85
 */
public interface CardIcon extends Copyable<CardIcon> {

    CardIconType getIconType();

    String getText();

    /**
     * Card hint on popup, support html and mana/restrict symbols
     *
     * @return
     */
    String getHint();

    /**
     * Whether or not this icon can be combined with others with the same icon type
     */
    default boolean canBeCombined() {
        return getText().isEmpty();
    }

    /**
     * Combined info (text + hint)
     *
     * @return
     */
    default String getCombinedInfo() {
        String res = getText();
        if (!getHint().isEmpty()) {
            res += res.isEmpty() ? "" : " - ";
            res += getHint();
        }
        return res;
    }
}