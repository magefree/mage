package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

public abstract class TransformingDoubleFacedCard extends DoubleFacedCard {

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, colorRight
        );
    }

    public TransformingDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String colorRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft, SpellAbilityType.TRANSFORMED);
        // main card name must be same as left side
        leftHalfCard = new TransformingDoubleFacedCardHalf(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.TRANSFORMED_LEFT
        );
        rightHalfCard = new TransformingDoubleFacedCardHalf(
                this.getOwnerId(), new CardSetInfo(secondSideName, setInfo),
                superTypesRight, typesRight, subTypesRight, colorRight, this
        );
        this.secondSideCard = rightHalfCard;
    }

    public TransformingDoubleFacedCard(final TransformingDoubleFacedCard card) {
        super(card);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.BASE) {
            return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
        }
        return super.cast(game, fromZone, ability, controllerId);
    }

    @Override
    public TransformingDoubleFacedCardHalf getLeftHalfCard() {
        return (TransformingDoubleFacedCardHalf) leftHalfCard;
    }

    @Override
    public TransformingDoubleFacedCardHalf getRightHalfCard() {
        return (TransformingDoubleFacedCardHalf) rightHalfCard;
    }
}
