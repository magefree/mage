package mage.abilities.common;

import java.util.Locale;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfEndStepTriggeredAbility extends TriggeredAbilityImpl {

    private TargetController targetController;
    private Condition interveningIfClauseCondition;

    public BeginningOfEndStepTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, null, isOptional);
    }

    public BeginningOfEndStepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, Condition interveningIfClauseCondition, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.interveningIfClauseCondition = interveningIfClauseCondition;
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
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
                }
                break;
            case ANY:
            case EACH_PLAYER:
            case NEXT:
                if (getTargets().isEmpty()) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.isControlledBy(event.getPlayerId())) {
                        if (getTargets().isEmpty()) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
                }
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

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(getEffects().getText(modes.getMode()));
        if (this.optional) {
            if (sb.substring(0, 6).toLowerCase(Locale.ENGLISH).equals("target")) {
                sb.insert(0, "you may have ");
            } else if (!sb.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("you ")) {
                sb.insert(0, "you may ");
            }
        }
        String abilityWordRule = "";
        if (abilityWord != null) {
            abilityWordRule = "<i>" + abilityWord.toString() + "</i> &mdash ";
        }
        switch (targetController) {
            case YOU:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of your end step, ").toString();
            case NEXT:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of the end step, ").toString();
            case OPPONENT:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of each opponent's end step, ").toString();
            case ANY:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of each end step, ").toString();
            case EACH_PLAYER:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of each player's end step, ").toString();
            case CONTROLLER_ATTACHED_TO:
                return sb.insert(0, generateConditionString()).insert(0, abilityWordRule + "At the beginning of the end step of enchanted permanent's controller, ").toString();
        }
        return "";
    }

    private String generateConditionString() {
        if (interveningIfClauseCondition != null) {
            if (interveningIfClauseCondition.toString().startsWith("if")) {
                return interveningIfClauseCondition.toString() + ", ";
            } else {
                return "if {this} is " + interveningIfClauseCondition.toString() + ", ";
            }
        }
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
}
