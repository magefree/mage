package mage.cards.t;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThirstForDiscovery extends CardImpl {

    public ThirstForDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw three cards. Then discard two cards unless you discard a basic land card.
        DiscardCardCost cost = new DiscardCardCost(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        cost.setText("discard a basic land card instead of discarding two cards");
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard a basic land card"));
    }

    private ThirstForDiscovery(final ThirstForDiscovery card) {
        super(card);
    }

    @Override
    public ThirstForDiscovery copy() {
        return new ThirstForDiscovery(this);
    }
}
