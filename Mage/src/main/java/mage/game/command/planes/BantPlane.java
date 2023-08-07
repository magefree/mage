package mage.game.command.planes;

import mage.ObjectColor;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStillOnPlaneCondition;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.condition.common.TargetHasCounterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.command.Plane;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class BantPlane extends Plane {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Green, White or Blue creatures");

    static {
        filter2.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE), new ColorPredicate(ObjectColor.BLUE)));
    }

    private static final String rule = "{this} has indestructible as long as it has a divinity counter on it";
    private static final String exaltedRule = "All creatures have exalted";

    public BantPlane() {
        this.setPlaneType(Planes.PLANE_BANT);

        // All creatures have exalted
        SimpleStaticAbility ability
                = new SimpleStaticAbility(Zone.COMMAND, new ConditionalContinuousEffect(
                new GainAbilityAllEffect(new ExaltedAbility(), Duration.Custom, StaticFilters.FILTER_PERMANENT_CREATURE),
                new IsStillOnPlaneCondition(this.getName()),
                exaltedRule));

        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, put a divinity counter on target green, white, or blue creature.  That creature gains indestructible for as long as it has a divinity counter on it.
        Effect chaosEffect = new ConditionalContinuousEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.Custom), new TargetHasCounterCondition(CounterType.DIVINITY), rule);
        Target chaosTarget = new TargetCreaturePermanent(1, 1, filter2, false);
        Effect chaosEffect2 = new AddCountersTargetEffect(CounterType.DIVINITY.createInstance());

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect2);
        chaosEffects.add(chaosEffect);

        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }

    private BantPlane(final BantPlane plane) {
        super(plane);
    }

    @Override
    public BantPlane copy() {
        return new BantPlane(this);
    }
}
