package mage.constants;

import mage.abilities.SpellAbility;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.Card;

/**
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback"),
    BESTOW("Bestow"),
    PROTOTYPE("Prototype"),
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

    public Card getTypeModifiedCardObjectCopy(Card card, SpellAbility spellAbility) {
        Card cardCopy = card.copy();
        if (this.equals(BESTOW)) {
            BestowAbility.becomeAura(cardCopy);
        }
        if (this.isTransformed){
            Card tmp = card.getSecondCardFace();
            if (tmp != null) {
                cardCopy = tmp.copy();
            }
        }
        if (this.equals(PROTOTYPE)){
            ((PrototypeAbility)spellAbility).transformCardSpellStatic(cardCopy);
        }
        return cardCopy;
    }
}
