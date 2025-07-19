
package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author stravant
 */
public final class StingingShot extends CardImpl {

    public StingingShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Put three -1/-1 counters on target creature with flying.
        getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance(3)));
        getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private StingingShot(final StingingShot card) {
        super(card);
    }

    @Override
    public StingingShot copy() {
        return new StingingShot(this);
    }
}
