
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BalduvianHydra extends CardImpl {

    public BalduvianHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{R}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Balduvian Hydra enters the battlefield with X +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P0.createInstance())));

        // Remove a +1/+0 counter from Balduvian Hydra: Prevent the next 1 damage that would be dealt to Balduvian Hydra this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new RemoveCountersSourceCost(CounterType.P1P0.createInstance())));

        // {R}{R}{R}: Put a +1/+0 counter on Balduvian Hydra. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P0.createInstance(1)), new ManaCostsImpl<>("{R}{R}{R}"), new IsStepCondition(PhaseStep.UPKEEP), null));

    }

    private BalduvianHydra(final BalduvianHydra card) {
        super(card);
    }

    @Override
    public BalduvianHydra copy() {
        return new BalduvianHydra(this);
    }
}
