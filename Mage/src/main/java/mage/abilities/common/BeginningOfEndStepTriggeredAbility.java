package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfEndStepTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final Condition interveningIfClauseCondition;

    public BeginningOfEndStepTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(effect, targetController, null, isOptional);
    }

    public BeginningOfEndStepTriggeredAbility(Effect effect, TargetController targetController, Condition interveningIfClauseCondition, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, interveningIfClauseCondition, isOptional);
    }

    public BeginningOfEndStepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, Condition interveningIfClauseCondition, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.interveningIfClauseCondition = interveningIfClauseCondition;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public BeginningOfEndStepTriggeredAbility(final BeginningOfEndStepTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.interveningIfClauseCondition = ability.interveningIfClauseCondition;
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
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours && getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
                break;
            case ANY:
            case EACH_PLAYER:
            case NEXT:
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment == null || attachment.getAttachedTo() == null) {
                    break;
                }
                Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                if (attachedTo == null || !attachedTo.isControlledBy(event.getPlayerId())) {
                    break;
                }
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            case ENCHANTED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                if (permanent == null || !game.isActivePlayer(permanent.getAttachedTo())) {
                    break;
                }
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            case MONARCH:
                if (!event.getPlayerId().equals(game.getMonarchId())) {
                    break;
                }
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        if (interveningIfClauseCondition != null) {
            return interveningIfClauseCondition.apply(game, this);
        }
        return true;
    }

    private String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your end step, " + generateConditionString();
            case NEXT:
                return "At the beginning of the end step, " + generateConditionString();
            case OPPONENT:
                return "At the beginning of each opponent's end step, " + generateConditionString();
            case ANY:
                return "At the beginning of each end step, " + generateConditionString();
            case EACH_PLAYER:
                return "At the beginning of each player's end step, " + generateConditionString();
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the end step of enchanted permanent's controller, " + generateConditionString();
            case ENCHANTED:
                return "At the beginning of enchanted player's end step, " + generateConditionString();
            case MONARCH:
                return "At the beginning of the monarch's end step, " + generateConditionString();
        }
        return "";
    }

    private String generateConditionString() {
        if (interveningIfClauseCondition == null) {
            if (getZone() == Zone.GRAVEYARD) {
                return "if {this} is in your graveyard, ";
            }
            return "";
        }
        String clauseText = interveningIfClauseCondition.toString();
        if (clauseText.startsWith("if")) {
            // Fixes punctuation on multiple sentence if-then construction
            // see -- Colfenor's Urn
            if (clauseText.endsWith(".")) {
                return clauseText + " ";
            }
            return clauseText + ", ";
        }
        return "if " + clauseText + ", ";
    }
}
