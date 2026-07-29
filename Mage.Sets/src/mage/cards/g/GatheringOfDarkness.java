package mage.cards.g;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class GatheringOfDarkness extends CardImpl {

    public GatheringOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Return up to one target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Amass Goblins 3.
        this.getSpellAbility().addEffect(new AmassEffect(3, SubType.GOBLIN));
    }

    private GatheringOfDarkness(final GatheringOfDarkness card) {
        super(card);
    }

    @Override
    public GatheringOfDarkness copy() {
        return new GatheringOfDarkness(this);
    }
}
