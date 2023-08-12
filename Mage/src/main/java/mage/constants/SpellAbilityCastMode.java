package mage.constants;

import mage.abilities.keyword.BestowAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback"),
    BESTOW("Bestow"),
    TRANSFORMED("Transformed", true),
    DISTURB("Disturb", true),
    MORE_THAN_MEETS_THE_EYE("More than Meets the Eye", true);

    private final String text;

    // Should the cast mode use the second face?
    private final boolean isTransformed;

    public boolean isTransformed() {
        return this.isTransformed;
    }

    SpellAbilityCastMode(String text) {
        this(text, false);
    }

    SpellAbilityCastMode(String text, boolean isTransformed) {
        this.text = text;
        this.isTransformed = isTransformed;
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
