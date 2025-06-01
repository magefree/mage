package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CoinFlippedEvent;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class WonCoinFlipControllerTriggeredAbility extends TriggeredAbilityImpl {

    public WonCoinFlipControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public WonCoinFlipControllerTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public WonCoinFlipControllerTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        this.setTriggerPhrase("Whenever you win a coin flip, ");
    }

    private WonCoinFlipControllerTriggeredAbility(final WonCoinFlipControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WonCoinFlipControllerTriggeredAbility copy() {
        return new WonCoinFlipControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return isControlledBy(event.getPlayerId()) && flipEvent.isWinnable() && flipEvent.wasWon();
    }
}
