
package mage.game.command.planes;

import java.util.ArrayList;
import java.util.List;
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
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.Target;
import mage.watchers.common.PlanarRollWatcher;

/**
 *
 * @author spjspj
 */
public class PanopticonPlane extends Plane {

    private static final String rule = "At the beginning of your draw step, draw an additional card";

    public PanopticonPlane() {
        this.setName("Plane - Panopticon");
        this.setExpansionSetCodeForImage("PCA");

        // At the beginning of your draw step, draw an additional card.
        Ability ability = new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1), TargetController.ANY, false);
        this.getAbilities().add(ability);
        Ability pwability = new PanopticonTriggeredAbility(new DrawCardTargetEffect(1));
        this.getAbilities().add(pwability);

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
        return event.getType() == EventType.PLANESWALKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Plane cPlane = game.getState().getCurrentPlane();
        if (cPlane == null || !cPlane.getName().equalsIgnoreCase("Plane - Panopticon")) {
            return false;
        }

        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            activePlayer.drawCards(1, game);
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
