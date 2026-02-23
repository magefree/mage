
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class SpikeSoldier extends CardImpl {

    public SpikeSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SPIKE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spike Soldier enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(3)));
        // {2}, Remove a +1/+1 counter from Spike Soldier: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Remove a +1/+1 counter from Spike Soldier: Spike Soldier gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance())));
    }

    private SpikeSoldier(final SpikeSoldier card) {
        super(card);
    }

    @Override
    public SpikeSoldier copy() {
        return new SpikeSoldier(this);
    }
}
