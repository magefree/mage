package mage.constants;

import mage.abilities.SpellAbility;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback"),
    BESTOW("Bestow"),
    PROTOTYPE("Prototype"),
    MORPH("Morph", false, true),
    MEGAMORPH("Megamorph", false, true),
    DISGUISE("Disguise", false, true),
    TRANSFORMED("Transformed", true),
    DISTURB("Disturb", true),
    MORE_THAN_MEETS_THE_EYE("More than Meets the Eye", true);

    private final String text;

    // should the cast mode use the second face?
    private final boolean isTransformed;

    private final boolean isFaceDown;

    public boolean isTransformed() {
        return this.isTransformed;
    }

    SpellAbilityCastMode(String text) {
        this(text, false);
    }

    SpellAbilityCastMode(String text, boolean isTransformed) {
        this(text, isTransformed, false);
    }

    SpellAbilityCastMode(String text, boolean isTransformed, boolean isFaceDown) {
        this.text = text;
        this.isTransformed = isTransformed;
        this.isFaceDown = isFaceDown;
    }

    public boolean isFaceDown() {
        return this.isFaceDown;
    }

    @Override
    public String toString() {
        return text;
    }

    public Card getTypeModifiedCardObjectCopy(Card card, SpellAbility spellAbility, Game game) {
        Card cardCopy = card.copy();
        if (this.isTransformed) {
            Card tmp = card.getSecondCardFace();
            if (tmp != null) {
                cardCopy = tmp.copy();
            }
        }

        switch (this) {
            case BESTOW:
                BestowAbility.becomeAura(cardCopy);
                break;
            case PROTOTYPE:
                cardCopy = ((PrototypeAbility) spellAbility).prototypeCardSpell(cardCopy);
                break;
            case MORPH:
            case MEGAMORPH:
            case DISGUISE:
                if (cardCopy instanceof Spell) {
                    //Spell doesn't support setName, so make a copy of the card (we're blowing it away anyway)
                    // TODO: research - is it possible to apply face down code to spell instead workaround with card
                    cardCopy = ((Spell) cardCopy).getCard().copy();
                }
                BecomesFaceDownCreatureEffect.FaceDownType faceDownType = BecomesFaceDownCreatureEffect.FaceDownType.MORPHED;
                if (this == DISGUISE) {
                    faceDownType = BecomesFaceDownCreatureEffect.FaceDownType.DISGUISED;
                }
                // no needs in additional abilities for spell
                BecomesFaceDownCreatureEffect.makeFaceDownObject(game, null, cardCopy, faceDownType, null);
                break;
            case NORMAL:
            case MADNESS:
            case FLASHBACK:
            case DISTURB:
            case MORE_THAN_MEETS_THE_EYE:
                // it changes only cost, so keep other characteristics
                // TODO: research - why TRANSFORMED here - is it used in this.isTransformed code?!
                break;
            case TRANSFORMED:
                // TODO: research - why TRANSFORMED here - is it used in this.isTransformed code?!
                break;
            default:
                throw new IllegalArgumentException("Un-supported ability cast mode: " + this);
        }

        return cardCopy;
    }
}
