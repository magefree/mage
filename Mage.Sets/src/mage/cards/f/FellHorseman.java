package mage.cards.f;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class FellHorseman extends AdventureCard {

    public FellHorseman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.KNIGHT}, "{3}{B}",
                "Deathly Ride",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Fell Horseman
        this.getLeftHalfCard().setPT(3, 3);

        // When Fell Horseman dies, put it on the bottom of its owner's library.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new PutOnLibrarySourceEffect(
                false, "put it on the bottom of its owner's library"
        ), false, SetTargetPointer.CARD));

        // Deathly Ride
        // Return target creature card from your graveyard to your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        finalizeCard();
    }

    private FellHorseman(final FellHorseman card) {
        super(card);
    }

    @Override
    public FellHorseman copy() {
        return new FellHorseman(this);
    }
}
