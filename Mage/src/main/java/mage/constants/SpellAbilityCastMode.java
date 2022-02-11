package mage.constants;

import mage.abilities.keyword.BestowAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback"),
    BESTOW("Bestow"),
    DISTURB("Disturb");

    private final String text;

    SpellAbilityCastMode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public Card getTypeModifiedCardObjectCopy(Card card, Game game) {
        Card cardCopy = card.copy();
        if (this.equals(BESTOW)) {
            BestowAbility.becomeAura(cardCopy);
        }
        return cardCopy;
    }
}
