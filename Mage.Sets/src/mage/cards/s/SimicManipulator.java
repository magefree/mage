package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * Gatecrash FAQ (01.2013)
 *
 * The power of the target creature is checked both as you target it and as the
 * ability resolves. If the power of the target creature when the ability
 * resolves is greater than the number of +1/+1 counters removed from Simic
 * Manipulator, the ability will be countered and none of its effects will
 * happen. You won't gain control of any creature, but the counters removed as a
 * cost remain removed.
 *
 * @author LevelX2
 */
public final class SimicManipulator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to the number of +1/+1 counters removed this way");

    public SimicManipulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Evolve
        this.addAbility(new EvolveAbility());

        // {T}, Remove one or more +1/+1 counters from Simic Manipulator: Gain control of target creature with power less than or equal to the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.Custom, true), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1, 1, "Remove one or more +1/+1 counters from {this}"));
        ability.setTargetAdjuster(new PowerTargetAdjuster(RemovedCountersForCostValue.instance, ComparisonType.OR_LESS));
        this.addAbility(ability);
    }

    private SimicManipulator(final SimicManipulator card) {
        super(card);
    }

    @Override
    public SimicManipulator copy() {
        return new SimicManipulator(this);
    }
}
