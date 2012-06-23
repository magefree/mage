package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfCombatTriggeredAbility extends TriggeredAbilityImpl<BeginningOfCombatTriggeredAbility> {
    private Constants.TargetController targetController;

    public BeginningOfCombatTriggeredAbility(Effect effect, Constants.TargetController targetController, boolean isOptional) {
        this(Constants.Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfCombatTriggeredAbility(Constants.Zone zone, Effect effect, Constants.TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
    }

    public BeginningOfCombatTriggeredAbility(final BeginningOfCombatTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public BeginningOfCombatTriggeredAbility copy() {
        return new BeginningOfCombatTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
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
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your combat step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case OPPONENT:
                return "At the beginning of each opponent's combat step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case ANY:
                return "At the beginning of each player's combat step, " + generateZoneString() + getEffects().getText(modes.getMode());
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
