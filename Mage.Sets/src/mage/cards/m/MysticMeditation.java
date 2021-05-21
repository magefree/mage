package mage.cards.m;

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
 * @author LevelX2
 */
public final class MysticMeditation extends CardImpl {

    public MysticMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw three cards. Then discard two cards unless you discard a creature card.
        DiscardCardCost cost = new DiscardCardCost(StaticFilters.FILTER_CARD_CREATURE_A);
        cost.setText("discard a creature card instead of discarding two cards");
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard a creature card"));
    }

    private MysticMeditation(final MysticMeditation card) {
        super(card);
    }

    @Override
    public MysticMeditation copy() {
        return new MysticMeditation(this);
    }
}
