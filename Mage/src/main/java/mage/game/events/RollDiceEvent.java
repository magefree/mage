package mage.game.events;

import mage.abilities.Ability;
import mage.constants.RollDieType;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class RollDiceEvent extends GameEvent {

    private final int sides;
    private int ignoreLowestAmount = 0; // ignore the lowest results
    private final RollDieType rollDieType;

    public RollDiceEvent(Ability source, RollDieType rollDieType, int sides, int rollsAmount) {
        super(EventType.ROLL_DICE, source.getControllerId(), source, source.getControllerId(), rollsAmount, false);
        this.sides = sides;
        this.rollDieType = rollDieType;
    }

    public int getSides() {
        return sides;
    }

    public RollDieType getRollDieType() {
        return rollDieType;
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
