package mage.cards;

import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * represents one half of an Omen card.
 * @author Jmlundeen
 */
public class OmenCardHalf extends CardWithSpellOptionHalf<OmenCard> {

    public OmenCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, OmenCard parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardSuperTypes, cardTypes, cardSubTypes, costs, parentCard, spellAbilityType);
    }

    @Override
    public String getSpellType() {
        return "Omen";
    }

    protected OmenCardHalf(final OmenCardHalf card) {
        super(card);
    }

    @Override
    public OmenCardHalf copy() {
        return new OmenCardHalf(this);
    }
}
