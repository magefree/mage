package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Loki
 */
public class BeginningOfUpkeepTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of your upkeep (optional = false)
     */
    public BeginningOfUpkeepTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    /**
     * At the beginning of your upkeep
     */
    public BeginningOfUpkeepTriggeredAbility(Effect effect, boolean optional) {
        this(TargetController.YOU, effect, optional);
    }

    public BeginningOfUpkeepTriggeredAbility(TargetController targetController, Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, targetController, effect, optional);
    }


    public BeginningOfUpkeepTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean optional) {
        super(zone, targetController, effect, optional);
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
            case NOT_YOU:
                return "At the beginning of each other player's upkeep, ";
            case OPPONENT:
                return "At the beginning of each opponent's upkeep, ";
            case ANY:
                return "At the beginning of each upkeep, ";
            case EACH_PLAYER:
                return "At the beginning of each player's upkeep, ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the upkeep of enchanted creature's controller, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's upkeep, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfUpkeepTriggeredAbility: " + targetController);
        }
    }

}
