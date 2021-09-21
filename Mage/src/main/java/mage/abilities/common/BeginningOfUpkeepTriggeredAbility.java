package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class BeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final boolean setTargetPointer;
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
                if (yours && setTargetPointer && getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return yours;
            case NOT_YOU:
                boolean notYours = !event.getPlayerId().equals(this.controllerId);
                if (notYours && setTargetPointer && getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return notYours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (setTargetPointer && getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
                break;
            case ANY:
            case ACTIVE:
            case EACH_PLAYER:
                if (setTargetPointer && getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.isControlledBy(event.getPlayerId())) {
                        if (setTargetPointer && getTargets().isEmpty()) {
                            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return true;
                    }
                }
                break;
            case ENCHANTED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                if (permanent == null || !game.isActivePlayer(permanent.getAttachedTo())) {
                    break;
                }
                if (setTargetPointer && getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            default:
                throw new UnsupportedOperationException("Value for targetController not supported: " + targetController);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        if (ruleTrigger != null && !ruleTrigger.isEmpty()) {
            return ruleTrigger;
        }
        switch (targetController) {
            case YOU:
                return "At the beginning of your upkeep, " + generateZoneString();
            case OPPONENT:
                return "At the beginning of each opponent's upkeep, " + generateZoneString();
            case ANY:
            case ACTIVE:
                return "At the beginning of each player's upkeep, " + generateZoneString();
            case EACH_PLAYER:
                return "At the beginning of each upkeep, " + generateZoneString();
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the upkeep of enchanted creature's controller, " + generateZoneString();
            case ENCHANTED:
                return "At the beginning of enchanted player's upkeep, " + generateZoneString();
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
