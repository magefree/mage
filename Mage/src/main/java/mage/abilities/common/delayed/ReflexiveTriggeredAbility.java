package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class ReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private final String text;

    public ReflexiveTriggeredAbility(Effect effect, boolean optional, String text) {
        super(effect, Duration.EndOfTurn, true, optional);
        this.text = text;
    }

    protected ReflexiveTriggeredAbility(final ReflexiveTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return text.substring(0, 1).toUpperCase() + text.substring(1) + '.';
    }

    public String getText() {
        return text;
    }

    @Override
    public ReflexiveTriggeredAbility copy() {
        return new ReflexiveTriggeredAbility(this);
    }
}
