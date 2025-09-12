package mage.cards;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

public class TransformingDoubleFacedCardHalf extends DoubleFacedCardHalf {

    public TransformingDoubleFacedCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, TransformingDoubleFacedCard parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardSuperTypes, cardTypes, cardSubTypes, costs, parentCard, spellAbilityType);
    }

    protected TransformingDoubleFacedCardHalf(final TransformingDoubleFacedCardHalf card) {
        super(card);
        this.parentCard = card.parentCard;
    }

    public TransformingDoubleFacedCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight, TransformingDoubleFacedCard parentCard) {
        super(ownerId, setInfo, superTypesRight, typesRight, subTypesRight, "", parentCard, SpellAbilityType.TRANSFORMED_RIGHT);
        this.getColor().setColor(new ObjectColor(colorRight));
    }

    @Override
    public TransformingDoubleFacedCardHalf copy() {
        return new TransformingDoubleFacedCardHalf(this);
    }

    @Override
    public TransformingDoubleFacedCard getParentCard() {
        return (TransformingDoubleFacedCard) parentCard;
    }
}
