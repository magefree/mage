package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfDrawTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;

    /**
     * The Ability sets if no target is defined the target pointer to the active
     * player of the current draw phase
     *
     * @param effect
     * @param targetController
     * @param isOptional
     */
    public BeginningOfDrawTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfDrawTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public BeginningOfDrawTriggeredAbility(final BeginningOfDrawTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public BeginningOfDrawTriggeredAbility copy() {
        return new BeginningOfDrawTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
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
            case NOT_YOU:
                if (!this.controllerId.equals(event.getPlayerId())) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.isControlledBy(event.getPlayerId())) {
                        if (getTargets().isEmpty()) {
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
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            case ANY:
            case ACTIVE:
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
        switch (targetController) {
            case ACTIVE:
            case YOU:
                return "At the beginning of your draw step, " + generateZoneString();
            case OPPONENT:
                return "At the beginning of each opponent's draw step, " + generateZoneString();
            case NOT_YOU:
                return "At the beginning of each other player's draw step, " + generateZoneString();
            case ANY:
                return "At the beginning of each player's draw step, " + generateZoneString();
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the draw step of enchanted creature's controller, " + generateZoneString();
            case ENCHANTED:
                return "At the beginning of enchanted player's draw step, " + generateZoneString();
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
