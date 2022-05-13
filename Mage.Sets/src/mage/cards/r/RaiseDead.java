package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class RaiseDead extends CardImpl {

    public RaiseDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
    }

    private RaiseDead(final RaiseDead card) {
        super(card);
    }

    @Override
    public RaiseDead copy() {
        return new RaiseDead(this);
    }
}
