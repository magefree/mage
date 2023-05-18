package mage.cards;

import mage.abilities.SpellAbility;
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
        super(
                ownerId, setInfo, typesLeft, costsLeft + costsRight, SpellAbilityType.MODAL,
                new DoubleFacedCardHalfImpl(
                        ownerId, setInfo.copy(),
                        superTypesLeft, typesLeft, subTypesLeft, costsLeft,
                        SpellAbilityType.MODAL_LEFT
                ),
                new DoubleFacedCardHalfImpl(
                        ownerId, new CardSetInfo(secondSideName, setInfo),
                        superTypesRight, typesRight, subTypesRight, costsRight,
                        SpellAbilityType.MODAL_RIGHT
                )
        );
    }

    public ModalDoubleFacedCard(ModalDoubleFacedCard card) {
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
                if (this.leftHalfCard.getSpellAbility() != null)
                    this.leftHalfCard.getSpellAbility().setControllerId(controllerId);
                if (this.rightHalfCard.getSpellAbility() != null)
                    this.rightHalfCard.getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }
}
