package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfDrawTriggeredAbility extends TriggeredAbilityImpl<BeginningOfDrawTriggeredAbility> {
    private Constants.TargetController targetController;

    public BeginningOfDrawTriggeredAbility(Effect effect, Constants.TargetController targetController, boolean isOptional) {
        this(Constants.Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfDrawTriggeredAbility(Constants.Zone zone, Effect effect, Constants.TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DRAW_STEP_PRE) {
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
                return "At the beginning of your draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case OPPONENT:
                return "At the beginning of each opponent's draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case ANY:
                return "At the beginning of each player's draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
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
