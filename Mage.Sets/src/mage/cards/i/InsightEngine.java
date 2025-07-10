package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsightEngine extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CHARGE);

    public InsightEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {2}, {T}: Put a charge counter on this artifact, then draw a card for each charge counter on it.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText(", then draw a card for each charge counter on it"));
        this.addAbility(ability);
    }

    private InsightEngine(final InsightEngine card) {
        super(card);
    }

    @Override
    public InsightEngine copy() {
        return new InsightEngine(this);
    }
}
