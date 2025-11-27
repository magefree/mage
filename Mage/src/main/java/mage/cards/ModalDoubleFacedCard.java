package mage.cards;

import mage.abilities.*;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author JayDi85
 */
public abstract class ModalDoubleFacedCard extends DoubleFacedCard {

    public ModalDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        this(
                ownerId, setInfo,
                new SuperType[]{}, typesLeft, subTypesLeft, costsLeft,
                secondSideName,
                new SuperType[]{}, typesRight, subTypesRight, costsRight
        );
    }

    public ModalDoubleFacedCard(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] superTypesLeft, CardType[] typesLeft, SubType[] subTypesLeft, String costsLeft,
            String secondSideName,
            SuperType[] superTypesRight, CardType[] typesRight, SubType[] subTypesRight, String costsRight
    ) {
        super(ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL);
        // main card name must be same as left side
        leftHalfCard = new ModalDoubleFacedCardHalf(
                this.getOwnerId(), setInfo.copy(),
                superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                this, SpellAbilityType.MODAL_LEFT
        );
        rightHalfCard = new ModalDoubleFacedCardHalf(
                this.getOwnerId(), new CardSetInfo(secondSideName, setInfo),
                superTypesRight, typesRight, subTypesRight, costsRight,
                this, SpellAbilityType.MODAL_RIGHT
        );
        this.secondSideCard = rightHalfCard;
    }

    public ModalDoubleFacedCard(final ModalDoubleFacedCard card) {
        super(card);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case MODAL_LEFT:
                return this.leftHalfCard.cast(game, fromZone, ability, controllerId);
            case MODAL_RIGHT:
                return this.rightHalfCard.cast(game, fromZone, ability, controllerId);
            default:
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public boolean isTransformable() {
        return this.getLeftHalfCard().isPermanent() && this.getRightHalfCard().isPermanent();
    }

    @Override
    public ModalDoubleFacedCardHalf getLeftHalfCard() {
        return (ModalDoubleFacedCardHalf) leftHalfCard;
    }

    @Override
    public ModalDoubleFacedCardHalf getRightHalfCard() {
        return (ModalDoubleFacedCardHalf) rightHalfCard;
    }
}
