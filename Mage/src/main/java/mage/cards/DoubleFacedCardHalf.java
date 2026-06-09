package mage.cards;

import mage.constants.*;
import mage.game.Game;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author JayDi85 - originally from ModalDoubleFaceCardHalf
 * @param <C> the type of the parent card
 */
public abstract class DoubleFacedCardHalf<C extends DoubleFacedCard<?, C>> extends CardPart<C> {

    public DoubleFacedCardHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, C parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardTypes, costs, parentCard, spellAbilityType);
        this.supertype.addAll(Arrays.asList(cardSuperTypes));
        this.subtype.addAll(Arrays.asList(cardSubTypes));
    }

    protected DoubleFacedCardHalf(final DoubleFacedCardHalf<C> card) {
        super(card);
    }

    @Override
    public boolean isTransformable() {
        return getOtherSide().isPermanent();
    }

    @Override
    public void setZone(Zone zone, Game game) {
        // see DoubleFacedCard.checkGoodZones for details
        game.setZone(getParentCard().getId(), zone);
        game.setZone(this.getId(), zone);

        // find another side to sync
        Card otherSide = getOtherSide();

        switch (zone) {
            case STACK:
            case BATTLEFIELD:
                // stack and battlefield must have only one side
                game.setZone(otherSide.getId(), Zone.OUTSIDE);
                break;
            default:
                game.setZone(otherSide.getId(), zone);
                break;
        }
        getParentCard().checkGoodZones(game);
    }

    public boolean isBackSide() {
        if (getParentCard().getLeftHalfCard().getId().equals(this.getId())) {
            return false;
        } else if (getParentCard().getRightHalfCard().getId().equals(this.getId())) {
            return true;
        } else {
            throw new IllegalStateException("Wrong code usage: MDF halves must use different ids");
        }
    }
}
