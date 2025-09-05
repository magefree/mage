package mage.cards.t;

import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Terrasymbiosis extends CardImpl {

    private static final DynamicValue xValue = new EffectKeyValue("countersAdded", "that many");

    public Terrasymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you put one or more +1/+1 counters on a creature you control, you may draw that many cards. Do this only once each turn.
        this.addAbility(new PutCounterOnPermanentTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue),
                CounterType.P1P1, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setDoOnlyOnceEachTurn(true));
    }

    private Terrasymbiosis(final Terrasymbiosis card) {
        super(card);
    }

    @Override
    public Terrasymbiosis copy() {
        return new Terrasymbiosis(this);
    }
}
