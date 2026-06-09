package mage.cards;

import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * represents one half of an Adventure card.
 * @author Jmlundeen
 */
public class AdventureCardHalf extends CardWithSpellOptionHalf<AdventureCard> {

    public AdventureCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, AdventureCard parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardSuperTypes, cardTypes, cardSubTypes, costs, parentCard, spellAbilityType);
    }

    @Override
    public String getSpellType() {
        return "Adventure";
    }

    protected AdventureCardHalf(final AdventureCardHalf card) {
        super(card);
    }

    @Override
    public AdventureCardHalf copy() {
        return new AdventureCardHalf(this);
    }
}
