package mage.constants;

import mage.abilities.SpellAbility;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LevelX2
 */
public enum SpellAbilityCastMode {
    NORMAL("Normal"),
    MADNESS("Madness"),
    FLASHBACK("Flashback"),
    BESTOW("Bestow"),
    PROTOTYPE("Prototype"),
    MORPH("Morph", false, SpellAbilityCastMode.MORPH_ADDITIONAL_RULE),
    MEGAMORPH("Megamorph", false, SpellAbilityCastMode.MORPH_ADDITIONAL_RULE),
    TRANSFORMED("Transformed", true),
    DISTURB("Disturb", true),
    MORE_THAN_MEETS_THE_EYE("More than Meets the Eye", true);

    private static final String MORPH_ADDITIONAL_RULE = "You may cast this card as a 2/2 face-down creature, with no text,"
            + " no name, no subtypes, and no mana cost by paying {3} rather than paying its mana cost.";

    private final String text;

    // should the cast mode use the second face?
    private final boolean isTransformed;

    // use it to add additional info in stack object cause face down has nothing
    // TODO: is it possible to use InfoEffect or CardHint instead that?
    private final List<String> additionalRulesOnStack;

    public boolean isTransformed() {
        return this.isTransformed;
    }

    SpellAbilityCastMode(String text) {
        this(text, false);
    }

    SpellAbilityCastMode(String text, boolean isTransformed) {
        this(text, isTransformed, null);
    }

    SpellAbilityCastMode(String text, boolean isTransformed, String additionalRulesOnStack) {
        this.text = text;
        this.isTransformed = isTransformed;
        this.additionalRulesOnStack = additionalRulesOnStack == null ? null : Collections.singletonList(additionalRulesOnStack);
    }

    public List<String> getAdditionalRulesOnStack() {
        return additionalRulesOnStack;
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
                if (cardCopy instanceof Spell) {
                    //Spell doesn't support setName, so make a copy of the card (we're blowing it away anyway)
                    // TODO: research - is it possible to apply face down code to spell instead workaround with card
                    cardCopy = ((Spell) cardCopy).getCard().copy();
                }
                BecomesFaceDownCreatureEffect.makeFaceDownObject(game, null, cardCopy, BecomesFaceDownCreatureEffect.FaceDownType.MORPHED, null);
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
