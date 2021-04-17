package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */

public class AtTheBeginOfNextCleanupDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtTheBeginOfNextCleanupDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom);
    }

    public AtTheBeginOfNextCleanupDelayedTriggeredAbility(Effect effect, Duration duration) {
        super(effect, duration);
    }

    public AtTheBeginOfNextCleanupDelayedTriggeredAbility(AtTheBeginOfNextCleanupDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfNextCleanupDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextCleanupDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLEANUP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        String text = modes.getText();
        if (!text.isEmpty()) {
            sb.append(Character.toUpperCase(text.charAt(0)));
            if (text.endsWith(".")) {
                sb.append(text.substring(1, text.length()-1));
            } else {
                sb.append(text.substring(1));
            }
        }
        return sb.append(" at the beginning of the next cleanup step.").toString();
    }
}
