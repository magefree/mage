
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class SpikeWorker extends CardImpl {

    public SpikeWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SPIKE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spike Worker enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(2)));
        // {2}, Remove a +1/+1 counter from Spike Worker: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpikeWorker(final SpikeWorker card) {
        super(card);
    }

    @Override
    public SpikeWorker copy() {
        return new SpikeWorker(this);
    }
}
