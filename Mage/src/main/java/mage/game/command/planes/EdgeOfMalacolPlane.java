package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author spjspj
 */
public class EdgeOfMalacolPlane extends Plane {

    public EdgeOfMalacolPlane() {
        this.setPlaneType(Planes.PLANE_EDGE_OF_MALACOL);

        // If a creature you control would untap during your untap step, put two +1/+1 counters on it instead.
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.COMMAND, new EdgeOfMalacolEffect());
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, untap each creature you control
        Effect chaosEffect = new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap each creature you control");
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

    private EdgeOfMalacolPlane(final EdgeOfMalacolPlane plane) {
        super(plane);
    }

    @Override
    public EdgeOfMalacolPlane copy() {
        return new EdgeOfMalacolPlane(this);
    }
}

class EdgeOfMalacolEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public EdgeOfMalacolEffect() {
        super(Duration.Custom, Outcome.Detriment);
        this.staticText = "If a creature you control would untap during your untap step, put two +1/+1 counters on it instead";
    }

    public EdgeOfMalacolEffect(final EdgeOfMalacolEffect effect) {
        super(effect);
    }

    @Override
    public EdgeOfMalacolEffect copy() {
        return new EdgeOfMalacolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Prevent untap event of creatures of target player
        if (game.getTurnStepType() == PhaseStep.UNTAP) {
            Plane cPlane = game.getState().getCurrentPlane();
            if (cPlane == null) {
                return false;
            }
            if (!cPlane.getPlaneType().equals(Planes.PLANE_EDGE_OF_MALACOL)) {
                return false;
            }
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (filter.match(permanent, game) && Objects.equals(permanent.getControllerId(), game.getActivePlayerId())) {
                UUID oldController = source.getControllerId();
                source.setControllerId(game.getActivePlayerId());
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(2));
                effect.setTargetPointer(new FixedTarget(permanent, game));
                effect.apply(game, source);
                source.setControllerId(oldController);
                return true;
            }
        }
        return false;
    }
}
