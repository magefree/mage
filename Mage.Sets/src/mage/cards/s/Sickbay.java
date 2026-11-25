package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class Sickbay extends CardImpl {

    public Sickbay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // At the beginning of combat on your turn, put X +1/+1 counters on target creature you control,
        // where X is one plus the amount of life you gained this turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(
            CounterType.P1P1.createInstance(), new IntPlusDynamicValue(1, ControllerGainedLifeCount.instance)
        ).setText("put X +1/+1 counters on target creature you control, "
            + "where X is one plus the amount of life you gained this turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addHint(ControllerGainedLifeCount.getHint());
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private Sickbay(final Sickbay card) {
        super(card);
    }

    @Override
    public Sickbay copy() {
        return new Sickbay(this);
    }
}
