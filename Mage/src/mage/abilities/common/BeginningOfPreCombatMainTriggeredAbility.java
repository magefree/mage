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
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class BeginningOfPreCombatMainTriggeredAbility extends TriggeredAbilityImpl<BeginningOfPreCombatMainTriggeredAbility> {

    private TargetController targetController;
    private boolean setTargetPointer;

    public BeginningOfPreCombatMainTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional, false);
    }

    public BeginningOfPreCombatMainTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
    }

    public BeginningOfPreCombatMainTriggeredAbility(final BeginningOfPreCombatMainTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfPreCombatMainTriggeredAbility copy() {
        return new BeginningOfPreCombatMainTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE) {
            switch (targetController) {
                case YOU:
                    boolean yours = event.getPlayerId().equals(this.controllerId);
                    if (yours && setTargetPointer) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                    }
                    return yours;
                case OPPONENT:
                    if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                        if (setTargetPointer) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
            break;
                case ANY:
                    if (setTargetPointer) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your precombat main phase, " + generateZoneString() + getEffects().getText(modes.getMode());
            case OPPONENT:
                return "At the beginning of each opponent's precombat main phase, " + generateZoneString() + getEffects().getText(modes.getMode());
            case ANY:
                return "At the beginning of each precombat main phase, " + generateZoneString() + getEffects().getText(modes.getMode());
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
