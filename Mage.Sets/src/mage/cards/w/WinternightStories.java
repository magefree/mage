package mage.cards.w;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WinternightStories extends CardImpl {

    public WinternightStories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw three cards. Then discard two cards unless you discard a creature card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2),
                new DiscardCardCost(StaticFilters.FILTER_CARD_CREATURE)
                        .setText("discard a creature card instead of discarding two cards")
        ).setText("Then discard two cards unless you discard a creature card"));

        // Harmonize {4}{U}
        this.addAbility(new HarmonizeAbility(this, "{4}{U}"));
    }

    private WinternightStories(final WinternightStories card) {
        super(card);
    }

    @Override
    public WinternightStories copy() {
        return new WinternightStories(this);
    }
}
