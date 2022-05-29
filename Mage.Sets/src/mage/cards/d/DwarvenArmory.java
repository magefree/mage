
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DwarvenArmory extends CardImpl {

    public DwarvenArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // {2}, Sacrifice a land: Put a +2/+2 counter on target creature. Activate this ability only during any upkeep step.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new AddCountersTargetEffect(CounterType.P2P2.createInstance()),
                new ManaCostsImpl<>("{2}"),
                new IsStepCondition(PhaseStep.UPKEEP, false),
                null);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DwarvenArmory(final DwarvenArmory card) {
        super(card);
    }

    @Override
    public DwarvenArmory copy() {
        return new DwarvenArmory(this);
    }
}
