package mage.cards;

import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

public class ModalDoubleFacedCardHalf extends DoubleFacedCardHalf {

    public ModalDoubleFacedCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, ModalDoubleFacedCard parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardSuperTypes, cardTypes, cardSubTypes, costs, parentCard, spellAbilityType);
    }

    protected ModalDoubleFacedCardHalf(final ModalDoubleFacedCardHalf card) {
        super(card);
        this.parentCard = card.parentCard;
    }

    @Override
    public boolean isTransformable() {
        return getOtherSide().isPermanent();
    }

    @Override
    public ModalDoubleFacedCardHalf copy() {
        return new ModalDoubleFacedCardHalf(this);
    }

    @Override
    public ModalDoubleFacedCard getParentCard() {
        return (ModalDoubleFacedCard) parentCard;
    }
}
