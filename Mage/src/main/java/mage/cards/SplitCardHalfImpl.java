package mage.cards;

import mage.constants.CardType;
import mage.constants.SpellAbilityType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class SplitCardHalfImpl extends CardPart<SplitCard> implements SplitCardHalf {

    public SplitCardHalfImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs, SplitCard splitCardParent, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, splitCardParent, spellAbilityType);
    }

    protected SplitCardHalfImpl(final SplitCardHalfImpl card) {
        super(card);
    }

    @Override
    public SplitCardHalfImpl copy() {
        return new SplitCardHalfImpl(this);
    }
}
