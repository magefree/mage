package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FurnaceStrider extends CardImpl {

    public FurnaceStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Furnace Strider enters the battlefield with two oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(2)),
                "with two oil counters on it"
        ));

        // Remove an oil counter from Furnace Strider: Target creature you control gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance()),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private FurnaceStrider(final FurnaceStrider card) {
        super(card);
    }

    @Override
    public FurnaceStrider copy() {
        return new FurnaceStrider(this);
    }
}
