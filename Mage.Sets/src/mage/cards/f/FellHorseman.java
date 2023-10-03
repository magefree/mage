package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class FellHorseman extends AdventureCard {

    public FellHorseman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{B}", "Deathly Ride", "{1}{B}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Fell Horseman dies, put it on the bottom of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new PutOnLibrarySourceEffect(
                false, "put it on the bottom of its owner's library"
        ), false));

        // Deathly Ride
        // Return target creature card from your graveyard to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        this.finalizeAdventure();
    }

    private FellHorseman(final FellHorseman card) {
        super(card);
    }

    @Override
    public FellHorseman copy() {
        return new FellHorseman(this);
    }
}
