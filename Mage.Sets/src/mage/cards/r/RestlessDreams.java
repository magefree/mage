package mage.cards.r;

import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RestlessDreams extends CardImpl {

    public RestlessDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast Restless Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(StaticFilters.FILTER_CARD_CARDS, true));

        // Return X target creature cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private RestlessDreams(final RestlessDreams card) {
        super(card);
    }

    @Override
    public RestlessDreams copy() {
        return new RestlessDreams(this);
    }
}
