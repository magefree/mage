
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
public class WinsCoinFlipTriggeredAbility extends TriggeredAbilityImpl {

    public WinsCoinFlipTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever a player wins a coin flip, ");
    }

    public WinsCoinFlipTriggeredAbility(final WinsCoinFlipTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WinsCoinFlipTriggeredAbility copy() {
        return new WinsCoinFlipTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return flipEvent.isWinnable() && (flipEvent.getChosen() == flipEvent.getResult());
    }
}
