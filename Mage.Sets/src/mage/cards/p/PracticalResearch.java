package mage.cards.p;

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
public final class PracticalResearch extends CardImpl {

    public PracticalResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Draw four cards. Then discard two cards unless you discard an instant or sorcery card.
        DiscardCardCost cost = new DiscardCardCost(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        cost.setText("discard an instant or sorcery card instead of discarding two cards");
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard an instant or sorcery card"));
    }

    private PracticalResearch(final PracticalResearch card) {
        super(card);
    }

    @Override
    public PracticalResearch copy() {
        return new PracticalResearch(this);
    }
}
