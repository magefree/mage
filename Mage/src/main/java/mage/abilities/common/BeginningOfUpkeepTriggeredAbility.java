package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.triggers.AtStepTriggeredAbility;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Loki
 */
public class BeginningOfUpkeepTriggeredAbility extends AtStepTriggeredAbility {

    public BeginningOfUpkeepTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BeginningOfUpkeepTriggeredAbility(Effect effect, boolean isOptional) {
        this(TargetController.YOU, effect, isOptional);
    }

    public BeginningOfUpkeepTriggeredAbility(TargetController targetController, Effect effect, boolean isOptional) {
        this(Zone.BATTLEFIELD, targetController, effect, isOptional);
    }


    public BeginningOfUpkeepTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean isOptional) {
        super(zone, targetController, effect, isOptional);
    }

    protected BeginningOfUpkeepTriggeredAbility(final BeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
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
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your upkeep, ";
            case OPPONENT:
                return "At the beginning of each opponent's upkeep, ";
            case ANY:
            case ACTIVE:
                return "At the beginning of each player's upkeep, ";
            case EACH_PLAYER:
                return "At the beginning of each upkeep, ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the upkeep of enchanted creature's controller, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's upkeep, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfUpkeepTriggeredAbility: " + targetController);
        }
    }

}
