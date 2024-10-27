package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.triggers.AtStepTriggeredAbility;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class BeginningOfDrawTriggeredAbility extends AtStepTriggeredAbility {

    public BeginningOfDrawTriggeredAbility(Effect effect, TargetController targetController, boolean optional) {
        this(Zone.BATTLEFIELD, effect, targetController, optional);
    }

    public BeginningOfDrawTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean optional) {
        super(zone, targetController, effect, optional);
    }

    protected BeginningOfDrawTriggeredAbility(final BeginningOfDrawTriggeredAbility ability) {
        super(ability);
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
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your draw step, ";
            case OPPONENT:
                return "At the beginning of each opponent's draw step, ";
            case NOT_YOU:
                return "At the beginning of each other player's draw step, ";
            case EACH_PLAYER:
                return "At the beginning of each player's draw step, ";
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the draw step of enchanted creature's controller, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's draw step, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfDrawTriggeredAbility: " + targetController);
        }
    }

}
