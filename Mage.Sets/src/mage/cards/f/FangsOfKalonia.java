package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class FangsOfKalonia extends CardImpl {

    public FangsOfKalonia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{1}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new DoubleCountersTargetEffect(CounterType.P1P1).setText(
                ", then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {4}{G}{G}
        Ability overloadAbility = new OverloadAbility(this, new AddCountersAllEffect(CounterType.P1P1.createInstance(1),
                StaticFilters.FILTER_CONTROLLED_CREATURE), new ManaCostsImpl<>("{4}{G}{G}"));
        overloadAbility.addEffect(new DoubleCounterOnEachPermanentEffect(CounterType.P1P1,
                StaticFilters.FILTER_CONTROLLED_CREATURE).setText(
                        ", then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way"));
        this.addAbility(overloadAbility);
    }

    private FangsOfKalonia(final FangsOfKalonia card) {
        super(card);
    }

    @Override
    public FangsOfKalonia copy() {
        return new FangsOfKalonia(this);
    }
}
