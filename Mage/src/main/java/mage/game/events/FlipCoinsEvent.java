package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class FlipCoinsEvent extends GameEvent {

    private boolean isHeadsAndWon = false;

    public FlipCoinsEvent(UUID playerId, int amount, Ability source) {
        super(EventType.FLIP_COINS, playerId, source, playerId, amount, false);
    }

    public void setHeadsAndWon(boolean headsAndWon) {
        isHeadsAndWon = headsAndWon;
    }

    public boolean isHeadsAndWon() {
        return isHeadsAndWon;
    }
}
