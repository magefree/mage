package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

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
        this.getSpellAbility().setTargetAdjuster(RestlessDreamsAdjuster.instance);

    }

    private RestlessDreams(final RestlessDreams card) {
        super(card);
    }

    @Override
    public RestlessDreams copy() {
        return new RestlessDreams(this);
    }
}

enum RestlessDreamsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = 0;
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof DiscardXTargetCost) {
                xValue = ((DiscardXTargetCost) cost).getAmount();
            }
        }
        Target target = new TargetCardInYourGraveyard(xValue,
                new FilterCreatureCard(new StringBuilder(xValue).append(xValue != 1 ?
                        " creature cards" : "creature card").append(" from your graveyard").toString()));
        ability.addTarget(target);
    }
}
