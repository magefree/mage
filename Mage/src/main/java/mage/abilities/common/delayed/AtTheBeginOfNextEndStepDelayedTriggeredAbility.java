package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author North
 */
public class AtTheBeginOfNextEndStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final TargetController targetController;
    private final Condition condition;

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect) {
        this(effect, TargetController.ANY);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect, TargetController targetController) {
        this(effect, targetController, null);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect, TargetController targetController, Condition condition) {
        this(effect, targetController, condition, false);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect, TargetController targetController, Condition condition, boolean optional) {
        super(effect, Duration.Custom, true, optional);
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
        switch (targetController) {
            case ANY:
                break;
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
                    return false;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(getSourceId());
                if (attachment == null || attachment.getAttachedTo() == null) {
                    return false;
                }
                Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                if (attachedTo == null || !attachedTo.isControlledBy(event.getPlayerId())) {
                    return false;
                }
                break;
            default:
                throw new UnsupportedOperationException("TargetController not supported");
        }
        return condition == null || condition.apply(game, this);
    }

    @Override
    public AtTheBeginOfNextEndStepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextEndStepDelayedTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your next end step, ";
            case OPPONENT:
                return "At the beginning of an opponent's next end step, ";
            case ANY:
                return "At the beginning of the next end step, ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the next end step of enchanted creature's controller, ";
        }
        throw new UnsupportedOperationException("TargetController not supported");
    }
}
