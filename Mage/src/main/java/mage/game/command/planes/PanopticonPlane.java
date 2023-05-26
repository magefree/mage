package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MainPhaseStackEmptyCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.RollPlanarDieEffect;
import mage.abilities.effects.common.cost.PlanarDieRollCostIncreasingEffect;
import mage.constants.Planes;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlanarRollWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author spjspj
 */
public class PanopticonPlane extends Plane {

    private static final String rule = "At the beginning of your draw step, draw an additional card";

    public PanopticonPlane() {
        this.setPlaneType(Planes.PLANE_PANOPTICON);

        // When you planeswalk to Panopticon, draw a card
        Ability pwability = new PanopticonTriggeredAbility(new DrawCardTargetEffect(1));
        this.getAbilities().add(pwability);

        // At the beginning of your draw step, draw an additional card.
        Ability ability = new BeginningOfDrawTriggeredAbility(Zone.COMMAND, new DrawCardTargetEffect(1), TargetController.ACTIVE, false);
        this.getAbilities().add(ability);

        // Active player can roll the planar die: Whenever you roll {CHAOS}, draw a card
        Effect chaosEffect = new DrawCardSourceControllerEffect(1);
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

    private PanopticonPlane(final PanopticonPlane plane) {
        super(plane);
    }

    @Override
    public PanopticonPlane copy() {
        return new PanopticonPlane(this);
    }
}

class PanopticonTriggeredAbility extends TriggeredAbilityImpl {

    public PanopticonTriggeredAbility(Effect effect) {
        super(Zone.COMMAND, effect);
    }

    public PanopticonTriggeredAbility(final PanopticonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLANESWALKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null || !cPlane.getPlaneType().equals(Planes.PLANE_PANOPTICON)) {
            return false;
        }

        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            getEffects().setTargetPointer(new FixedTarget(activePlayer.getId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you planeswalk to {this}, draw a card";
    }

    @Override
    public PanopticonTriggeredAbility copy() {
        return new PanopticonTriggeredAbility(this);
    }
}
