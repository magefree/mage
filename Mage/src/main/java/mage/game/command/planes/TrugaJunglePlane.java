package mage.game.command.planes;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStillOnPlaneCondition;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.command.Plane;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class TrugaJunglePlane extends Plane {

    private static final String rule = "All lands have '{t}: Add one mana of any color";

    public TrugaJunglePlane() {
        this.setPlaneType(Planes.PLANE_TRUGA_JUNGLE);

        SimpleStaticAbility ability
                = new SimpleStaticAbility(Zone.COMMAND, new ConditionalContinuousEffect(
                new GainAbilityAllEffect(new AnyColorManaAbility(), Duration.Custom, StaticFilters.FILTER_LANDS),
                new IsStillOnPlaneCondition(this.getName()),
                rule));
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, reveal the top three cards of your libary.  Put all land cards revealed this way into your hand the rest on the bottom of your library in any order.
        Effect chaosEffect = new RevealLibraryPutIntoHandEffect(3, new FilterLandCard(), Zone.LIBRARY);
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<Effect>();
        chaosEffects.add(chaosEffect);
        List<Target> chaosTargets = new ArrayList<Target>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }

    private TrugaJunglePlane(final TrugaJunglePlane plane) {
        super(plane);
    }

    @Override
    public TrugaJunglePlane copy() {
        return new TrugaJunglePlane(this);
    }
}
