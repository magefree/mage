

package mage.cards.v;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class VisionOfLove extends CardImpl {

    public VisionOfLove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // You may sacrifice an artifact or discard a card. If you do, draw two cards.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new OrCost(
                    "sacrifice an artifact or discard a card",
                    new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                    new DiscardCardCost()
                )
        ));
    }

    private VisionOfLove(final VisionOfLove card) {
        super(card);
    }

    @Override
    public VisionOfLove copy() {
        return new VisionOfLove(this);
    }
}
