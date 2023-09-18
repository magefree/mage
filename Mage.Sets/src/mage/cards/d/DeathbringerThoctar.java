package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DeathbringerThoctar extends CardImpl {

    public DeathbringerThoctar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature dies, you may put a +1/+1 counter on Deathbringer Thoctar.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, true
        ));

        // Remove a +1/+1 counter from Deathbringer Thoctar: Deathbringer Thoctar deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(1, "it"),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DeathbringerThoctar(final DeathbringerThoctar card) {
        super(card);
    }

    @Override
    public DeathbringerThoctar copy() {
        return new DeathbringerThoctar(this);
    }
}
