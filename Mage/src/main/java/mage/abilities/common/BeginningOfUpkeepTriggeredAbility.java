
package mage.abilities.common;

import java.util.Locale;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class BeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    private TargetController targetController;
    private boolean setTargetPointer;
    protected String ruleTrigger;

    public BeginningOfUpkeepTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfUpkeepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional) {
        this(zone, effect, targetController, isOptional, true);
    }

    public BeginningOfUpkeepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        this(zone, effect, targetController, isOptional, setTargetPointer, null);
    }

    public BeginningOfUpkeepTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer, String ruleTrigger) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        this.ruleTrigger = ruleTrigger;
    }

    public BeginningOfUpkeepTriggeredAbility(final BeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
        this.ruleTrigger = ability.ruleTrigger;
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new BeginningOfUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours && setTargetPointer) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (setTargetPointer && getTargets().isEmpty()) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
                }
                break;
            case ANY:
                if (setTargetPointer && getTargets().isEmpty()) {
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
                        if (setTargetPointer && getTargets().isEmpty()) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("Value for targetController not supported: " + targetController.toString());
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        if (ruleTrigger != null && !ruleTrigger.isEmpty()) {
            return sb.insert(0, ruleTrigger).toString();
        }
        switch (targetController) {
            case YOU:
                if (this.optional) {
                    if (sb.substring(0, 6).toLowerCase(Locale.ENGLISH).equals("target")) {
                        sb.insert(0, "you may have ");
                    } else if (!sb.substring(0, 4).toLowerCase(Locale.ENGLISH).equals("you ")) {
                        sb.insert(0, "you may ");
                    }
                }
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of your upkeep, ").toString();
            case OPPONENT:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each opponent's upkeep, ").toString();
            case ANY:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each upkeep, ").toString();
            case CONTROLLER_ATTACHED_TO:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of the upkeep of enchanted creature's controller, ").toString();
        }
        return "";
    }

    private String generateZoneString() {
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
}
