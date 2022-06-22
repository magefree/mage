package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Locale;

/**
 * @author TheElk801
 */
public class ReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    private final String text;
    private final Condition condition;

    public ReflexiveTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, null);
    }

    public ReflexiveTriggeredAbility(Effect effect, boolean optional, String text) {
        this(effect, optional, text, null);
    }

    public ReflexiveTriggeredAbility(Effect effect, boolean optional, String text, Condition condition) {
        super(effect, Duration.EndOfTurn, true, optional);
        this.text = text;
        this.condition = condition;
    }

    protected ReflexiveTriggeredAbility(final ReflexiveTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
        this.condition = ability.condition;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.isControlledBy(event.getPlayerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        if (text == null) {
            return super.getRule();
        }
        return text.substring(0, 1).toUpperCase(Locale.ENGLISH) + text.substring(1) + '.';
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return condition == null || condition.apply(game, this);
    }

    @Override
    public ReflexiveTriggeredAbility copy() {
        return new ReflexiveTriggeredAbility(this);
    }
}
