package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.triggers.AtStepTriggeredAbility;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class BeginningOfEndStepTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of your end step (optional = false)
     */
    public BeginningOfEndStepTriggeredAbility(Effect effect) {
        this(TargetController.YOU, effect, false);
    }

    public BeginningOfEndStepTriggeredAbility(TargetController targetController, Effect effect, boolean optional) {
        this(effect, targetController, null, optional);
    }

    public BeginningOfEndStepTriggeredAbility(Effect effect, TargetController targetController, Condition interveningIfClauseCondition, boolean optional) {
        this(Zone.BATTLEFIELD, effect, targetController, interveningIfClauseCondition, optional);
    }

    public BeginningOfEndStepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, Condition interveningIfClauseCondition, boolean optional) {
        super(zone, targetController, effect, optional);
        this.withInterveningIf(interveningIfClauseCondition);
    }

    protected BeginningOfEndStepTriggeredAbility(final BeginningOfEndStepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfEndStepTriggeredAbility copy() {
        return new BeginningOfEndStepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your end step, ";
            case NEXT:
                return "At the beginning of the end step, ";
            case OPPONENT:
                return "At the beginning of each opponent's end step, ";
            case ANY:
                return "At the beginning of each end step, ";
            case EACH_PLAYER:
                return "At the beginning of each player's end step, ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the end step of enchanted permanent's controller, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's end step, ";
            case MONARCH:
                return "At the beginning of the monarch's end step, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfEndStepTriggeredAbility: " + targetController);
        }
    }

}
