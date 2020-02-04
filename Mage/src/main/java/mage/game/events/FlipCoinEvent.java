package mage.game.events;

import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 **/
public class FlipCoinEvent extends GameEvent {
    private boolean result;
    private final boolean chosen;
    private final boolean winnable;
    private int flipCount = 1;

    public FlipCoinEvent(UUID playerId, UUID sourceId, boolean result, boolean chosen, boolean winnable) {
        super(EventType.FLIP_COIN, playerId, sourceId, playerId);
        this.result = result;
        this.chosen = chosen;
        this.winnable = winnable;
    }

    public boolean getResult() {
        return result;
    }

    public String getResultName() {
        return CardUtil.booleanToFlipName(result);
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getChosen() {
        return chosen;
    }

    public String getChosenName() {
        return CardUtil.booleanToFlipName(chosen);
    }

    public boolean isWinnable() {
        return winnable;
    }

    public int getFlipCount() {
        return flipCount;
    }

    public void setFlipCount(int flipCount) {
        this.flipCount = flipCount;
    }

    public CoinFlippedEvent getFlippedEvent() {
        return new CoinFlippedEvent(playerId, sourceId, result, chosen, winnable);
    }
}
