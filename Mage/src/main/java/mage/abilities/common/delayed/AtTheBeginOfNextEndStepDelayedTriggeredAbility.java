
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class AtTheBeginOfNextEndStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private TargetController targetController;
    private Condition condition;

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect) {
        this(effect, TargetController.ANY);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect, TargetController targetController) {
        this(Zone.ALL, effect, targetController);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone zone, Effect effect, TargetController targetController) {
        this(zone, effect, targetController, null);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone zone, Effect effect, TargetController targetController, Condition condition) {
        super(effect, Duration.Custom);
        this.zone = zone;
        this.targetController = targetController;
        this.condition = condition;
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(final AtTheBeginOfNextEndStepDelayedTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.condition = ability.condition;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean correctEndPhase = false;
        switch (targetController) {
            case ANY:
                correctEndPhase = true;
                break;
            case YOU:
                correctEndPhase = event.getPlayerId().equals(this.controllerId);
                break;
            case OPPONENT:
                if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                    correctEndPhase = true;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.isControlledBy(event.getPlayerId())) {
                        correctEndPhase = true;
                    }
                }
        }
        if (correctEndPhase) {
            return !(condition != null && !condition.apply(game, this));
        }
        return false;
    }

    @Override
    public AtTheBeginOfNextEndStepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextEndStepDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("At the beginning of your next end step, ");
                break;
            case OPPONENT:
                sb.append("At the beginning of an opponent's next end step, ");
                break;
            case ANY:
                sb.append("At the beginning of the next end step, ");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("At the beginning of the next end step of enchanted creature's controller, ");
                break;
        }
        sb.append(getEffects().getText(modes.getMode()));
        return sb.toString();
    }
}
