package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl<BeginningOfUpkeepTriggeredAbility> {
    private Constants.TargetController targetController;

    public BeginningOfUpkeepTriggeredAbility(Effect effect, Constants.TargetController targetController, boolean isOptional) {
        this(Constants.Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfUpkeepTriggeredAbility(Constants.Zone zone, Effect effect, Constants.TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
    }

    public BeginningOfUpkeepTriggeredAbility(final BeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new BeginningOfUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            switch (targetController) {
                case YOU:
                    boolean yours = event.getPlayerId().equals(this.controllerId);
                    if (yours) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                    }
                    return yours;
                case OPPONENT:
                    if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
            break;
                case ANY:
                    if (getTargets().size() == 0) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
                case CONTROLLER_ATTACHED_TO:
                    Permanent attachment = game.getPermanent(sourceId);
                    if (attachment != null && attachment.getAttachedTo() != null) {
                        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                        if (attachedTo != null && attachedTo.getControllerId().equals(event.getPlayerId())) {
                            if (getTargets().size() == 0) {
                                for (Effect effect : this.getEffects()) {
                                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                                }
                            }
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(getEffects().getText(modes.getMode()));
        switch (targetController) {
            case YOU:
                if (this.optional) {
                    if (sb.substring(0, 6).toLowerCase().equals("target")){
                        sb.insert(0, "you may have ");
                    } else if (!sb.substring(0, 4).toLowerCase().equals("you ")){
                        sb.insert(0, "you may ");
                    }
                }
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of your upkeep, ").toString();
            case OPPONENT:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each opponent's upkeep, ").toString();
            case ANY:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each player's upkeep, ").toString();
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
