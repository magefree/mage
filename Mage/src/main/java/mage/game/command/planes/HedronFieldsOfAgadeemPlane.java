package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EldraziAnnihilatorToken;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class HedronFieldsOfAgadeemPlane extends Plane {

    public HedronFieldsOfAgadeemPlane() {
        this.setPlaneType(Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM);

        // Creatures with power 7 or greater can't attack or block 
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new HedronFieldsOfAgadeemRestrictionEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, create a 7/7 colorless Eldrazi creature with annhilator 1
        Effect chaosEffect = new CreateTokenEffect(new EldraziAnnihilatorToken());
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

    private HedronFieldsOfAgadeemPlane(final HedronFieldsOfAgadeemPlane plane) {
        super(plane);
    }

    @Override
    public HedronFieldsOfAgadeemPlane copy() {
        return new HedronFieldsOfAgadeemPlane(this);
    }
}

class HedronFieldsOfAgadeemRestrictionEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 7 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
    }

    public HedronFieldsOfAgadeemRestrictionEffect() {
        super(Duration.Custom);
        staticText = "Creatures with power 7 or greater can't attack or block";
    }

    public HedronFieldsOfAgadeemRestrictionEffect(final HedronFieldsOfAgadeemRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public HedronFieldsOfAgadeemRestrictionEffect copy() {
        return new HedronFieldsOfAgadeemRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null) {
            return false;
        }
        if (!cPlane.getPlaneType().equals(Planes.PLANE_HEDRON_FIELDS_OF_AGADEEM)) {
            return false;
        }
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
