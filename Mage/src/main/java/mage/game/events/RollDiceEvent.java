package mage.game.events;

import mage.abilities.Ability;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class RollDiceEvent extends GameEvent {

    private final int sides;
    private int ignoreLowestAmount = 0; // ignore the lowest results

    public RollDiceEvent(int sides, int amount, Ability source) {
        super(EventType.ROLL_DICE, source.getControllerId(), source, source.getControllerId(), amount, false);
        this.sides = sides;
        this.amount = amount;
    }

    public int getSides() {
        return sides;
    }

    public void incAmount(int additionalAmount) {
        this.amount = CardUtil.overflowInc(this.amount, additionalAmount);
    }

    public void incIgnoreLowestAmount(int additionalCount) {
        this.ignoreLowestAmount = CardUtil.overflowInc(this.ignoreLowestAmount, additionalCount);
    }

    public int getIgnoreLowestAmount() {
        return ignoreLowestAmount;
    }
}
