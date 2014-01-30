/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Jeff
 */
public class BeginningOfUntapTriggeredAbility extends TriggeredAbilityImpl<BeginningOfUntapTriggeredAbility> {
    
    private TargetController targetController;

    public BeginningOfUntapTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfUntapTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
    }

    public BeginningOfUntapTriggeredAbility(final BeginningOfUntapTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public BeginningOfUntapTriggeredAbility copy() {
        return new BeginningOfUntapTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
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
                case NOT_YOU:
                    Player controller = game.getPlayer(this.getControllerId());
                    if (controller != null && controller.getInRange().contains(event.getPlayerId()) && !event.getPlayerId().equals(this.getControllerId())) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
		    break;
                case OPPONENT:
                    if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
		    break;
                case ANY:
                    controller = game.getPlayer(this.getControllerId());
                    if (controller != null && controller.getInRange().contains(event.getPlayerId())) {
                        if (getTargets().size() == 0) {
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
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                return sb.append("At the beginning of your untap step, ").append(generateZoneString()).append(getEffects().getText(modes.getMode())).toString();
            case NOT_YOU:
                return sb.append(getEffects().getText(modes.getMode())).append(" during each other player's untap step").append(generateZoneString()).toString();
            case OPPONENT:
                return sb.append("At the beginning of each opponent's untap step, ").append(generateZoneString()).append(getEffects().getText(modes.getMode())).toString();
            case ANY:
                return sb.append("At the beginning of each player's untap step, ").append(generateZoneString()).append(getEffects().getText(modes.getMode())).toString();

        }
        return "BeginningOfUntapTriggeredAbility: targetController value not supported";
    }

    private String generateZoneString() {
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
    
}
