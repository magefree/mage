package mage.game.events;

import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 **/
public class CoinFlippedEvent extends GameEvent {
    private final boolean result;
    private final boolean chosen;
    private final boolean winnable;

    CoinFlippedEvent(UUID playerId, UUID sourceId, boolean result, boolean chosen, boolean winnable) {
        super(EventType.COIN_FLIPPED, playerId, sourceId, playerId);
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

    public boolean getChosen() {
        return chosen;
    }

    public String getChosenName() {
        return CardUtil.booleanToFlipName(chosen);
    }

    public boolean isWinnable() {
        return winnable;
    }
}
