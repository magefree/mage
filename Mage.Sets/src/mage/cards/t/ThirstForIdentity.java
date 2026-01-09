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
public final class ThirstForIdentity extends CardImpl {

    public ThirstForIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw three cards. Then discard two cards unless you discard a creature card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardCardCost(StaticFilters.FILTER_CARD_CREATURE)
                        .setText("discard a creature card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard a creature card"));
    }

    private ThirstForIdentity(final ThirstForIdentity card) {
        super(card);
    }

    @Override
    public ThirstForIdentity copy() {
        return new ThirstForIdentity(this);
    }
}
