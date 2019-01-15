package mage.game.events;

import java.util.UUID;

/**
 * @author TheElk801
 **/
public class FlipCoinEvent extends GameEvent {
    private boolean result;
    private final boolean chosen;
    private final boolean winnable;

    public FlipCoinEvent(UUID playerId, UUID sourceId, boolean result, boolean chosen, boolean winnable) {
        super(EventType.FLIP_COIN, playerId, sourceId, playerId);
        this.result = result;
        this.chosen = chosen;
        this.winnable = winnable;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getChosen() {
        return chosen;
    }

    public boolean isWinnable() {
        return winnable;
    }

    public CoinFlippedEvent getFlippedEvent() {
        return new CoinFlippedEvent(playerId, sourceId, result, chosen, winnable);
    }
}
