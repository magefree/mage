
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

public class AtTheBeginOfNextUpkeepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(Effect effect) {
        this(effect, Duration.Custom);
    }

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(Effect effect, Duration duration) {
        super(effect, duration);
    }

    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility(AtTheBeginOfNextUpkeepDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtTheBeginOfNextUpkeepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
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
        return sb.append(" at the beginning of the next turn's upkeep.").toString();
    }
}
