package mage.cards;

import mage.constants.*;
import mage.game.Game;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Jmlundeen
 * @param <C> the type of the parent card
 */
public abstract class CardWithSpellOptionHalf<C extends CardWithSpellOption<?, C>> extends CardPart<C> {

    public CardWithSpellOptionHalf(
            UUID ownerId, CardSetInfo setInfo,
            SuperType[] cardSuperTypes, CardType[] cardTypes, SubType[] cardSubTypes,
            String costs, C parentCard, SpellAbilityType spellAbilityType
    ) {
        super(ownerId, setInfo, cardTypes, costs, parentCard, spellAbilityType);
        this.supertype.addAll(Arrays.asList(cardSuperTypes));
        this.subtype.addAll(Arrays.asList(cardSubTypes));
    }

    protected CardWithSpellOptionHalf(final CardWithSpellOptionHalf<C> card) {
        super(card);
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

    public abstract String getSpellType();
}
