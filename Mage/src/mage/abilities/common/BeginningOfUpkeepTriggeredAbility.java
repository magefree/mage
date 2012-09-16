package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
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
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        String effectsText = getEffects().getText(modes.getMode());
        switch (targetController) {
            case YOU:
                if (this.optional) {
                    if (effectsText.toLowerCase().startsWith("target")){
                        effectsText = "you may have " + effectsText;
                    } else if (!effectsText.toLowerCase().startsWith("you may")){
                        effectsText = "you may " + effectsText;
                    }
                }
                return "At the beginning of your upkeep, " + generateZoneString() + effectsText;
            case OPPONENT:
                return "At the beginning of each opponent's upkeep, " + generateZoneString() + effectsText;
            case ANY:
                return "At the beginning of each player's upkeep, " + generateZoneString() + effectsText;
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
