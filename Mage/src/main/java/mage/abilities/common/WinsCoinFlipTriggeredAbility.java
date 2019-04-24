
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public class WinsCoinFlipTriggeredAbility extends TriggeredAbilityImpl {

    public WinsCoinFlipTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
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
        return event.getFlag();
    }

    @Override
    public String getRule() {
        return "Whenever a player wins a coin flip, " + super.getRule();
    }
}
