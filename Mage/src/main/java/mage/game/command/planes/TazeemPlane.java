package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class TazeemPlane extends Plane {

    private static final String rule = "Creatures can't block";

    public TazeemPlane() {
        this.setPlaneType(Planes.PLANE_TAZEEM);
        this.setExpansionSetCodeForImage("PCA");

        // Creatures can't block
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new TazeemCantBlockAllEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, draw a card for each land you control
        Effect chaosEffect = new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent()));
        Target chaosTarget = null;

        List<Effect> chaosEffects = new ArrayList<>();
        chaosEffects.add(chaosEffect);

        List<Target> chaosTargets = new ArrayList<>();
        chaosTargets.add(chaosTarget);

        ActivateIfConditionActivatedAbility chaosAbility = new ActivateIfConditionActivatedAbility(Zone.COMMAND, new RollPlanarDieEffect(chaosEffects, chaosTargets), new GenericManaCost(0), MainPhaseStackEmptyCondition.instance);
        chaosAbility.addWatcher(new PlanarRollWatcher());
        this.getAbilities().add(chaosAbility);
        chaosAbility.setMayActivate(TargetController.ANY);
        this.getAbilities().add(new SimpleStaticAbility(Zone.ALL, new PlanarDieRollCostIncreasingEffect(chaosAbility.getOriginalId())));
    }
}

class TazeemCantBlockAllEffect extends RestrictionEffect {

    private FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures");

    public TazeemCantBlockAllEffect() {
        super(Duration.Custom);
    }

    public TazeemCantBlockAllEffect(final TazeemCantBlockAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();

        if (cPlane == null || !cPlane.getPlaneType().equals(Planes.PLANE_TAZEEM)) {
            return false;
        }
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public TazeemCantBlockAllEffect copy() {
        return new TazeemCantBlockAllEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" can't block");
        return sb.toString();
    }
}
