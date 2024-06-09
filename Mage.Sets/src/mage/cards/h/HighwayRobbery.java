package mage.cards.h;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighwayRobbery extends CardImpl {

    public HighwayRobbery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may discard a card or sacrifice a land. If you do, draw two cards.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new OrCost(
                        "discard a card or sacrifice a land",
                        new DiscardCardCost(),
                        new SacrificeTargetCost(StaticFilters.FILTER_LAND_A)
                )
        ));

        // Plot {1}{R}
        this.addAbility(new PlotAbility("{1}{R}"));
    }

    private HighwayRobbery(final HighwayRobbery card) {
        super(card);
    }

    @Override
    public HighwayRobbery copy() {
        return new HighwayRobbery(this);
    }
}
