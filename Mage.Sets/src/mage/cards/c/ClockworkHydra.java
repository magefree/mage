package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ClockworkHydra extends CardImpl {

    public ClockworkHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Clockwork Hydra enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                "with four +1/+1 counters on it"
        ));

        // Whenever Clockwork Hydra attacks or blocks, remove a +1/+1 counter from it. If you do, Clockwork Hydra deals 1 damage to any target.
        Ability ability = new AttacksOrBlocksTriggeredAbility(new DoIfCostPaid(
                new DamageTargetEffect(1),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance()),
                null, false
        ).setText("remove a +1/+1 counter from it. If you do, {this} deals 1 damage to any target"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {tap}: Put a +1/+1 counter on Clockwork Hydra.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new TapSourceCost()
        ));
    }

    private ClockworkHydra(final ClockworkHydra card) {
        super(card);
    }

    @Override
    public ClockworkHydra copy() {
        return new ClockworkHydra(this);
    }
}
