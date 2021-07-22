package mage.cards.t;

import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrillingDiscovery extends CardImpl {

    public ThrillingDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{W}");

        // You gain 2 life. Then you may discard two cards. If you do, draw three cards.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(3),
                new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD))
        ).setText("Then you may discard two cards. If you do, draw three cards"));
    }

    private ThrillingDiscovery(final ThrillingDiscovery card) {
        super(card);
    }

    @Override
    public ThrillingDiscovery copy() {
        return new ThrillingDiscovery(this);
    }
}
