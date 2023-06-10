package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class TheRingTemptsYouTriggeredAbility extends TriggeredAbilityImpl {

    public TheRingTemptsYouTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever the Ring tempts you, ");
    }

    private TheRingTemptsYouTriggeredAbility(final TheRingTemptsYouTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRingTemptsYouTriggeredAbility copy() {
        return new TheRingTemptsYouTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TEMPTED_BY_RING;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
