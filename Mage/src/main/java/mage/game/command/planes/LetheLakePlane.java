package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Plane;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class LetheLakePlane extends Plane {

    public LetheLakePlane() {
        this.setPlaneType(Planes.PLANE_LETHE_LAKE);

        // At the beginning of your upkeep, put the top ten cards of your libary into your graveyard
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND, new MillCardsTargetEffect(10).setText("that player mills 10 cards"), TargetController.ANY, false, true);
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, target player puts the top ten cards of their library into their graveyard
        Effect chaosEffect = new MillCardsTargetEffect(10);
        Target chaosTarget = new TargetPlayer();

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

    private LetheLakePlane(final LetheLakePlane plane) {
        super(plane);
    }

    @Override
    public LetheLakePlane copy() {
        return new LetheLakePlane(this);
    }
}
