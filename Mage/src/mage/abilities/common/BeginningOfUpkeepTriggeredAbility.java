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
        super(Constants.Zone.BATTLEFIELD, effect, isOptional);
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
                    return event.getPlayerId().equals(this.controllerId);
                case OPPONENT:
                    if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return true;
                    }
					break;
                case ANY:
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
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
                return "At the beginning of your upkeep, " + getEffects().getText(modes.getMode());
            case OPPONENT:
                return "At the beginning of each opponent's upkeep, " + getEffects().getText(modes.getMode());
            case ANY:
                return "At the beginning of each player's upkeep, " + getEffects().getText(modes.getMode());
        }
        return "";
    }
}
