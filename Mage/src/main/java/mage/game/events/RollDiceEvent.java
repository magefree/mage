package mage.game.events;

import mage.abilities.Ability;
import mage.constants.RollDieType;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class RollDiceEvent extends GameEvent {

    private final int sides;
    private int ignoreLowestAmount = 0; // ignore the lowest results
    private final RollDieType rollDieType;

    /**
     * The target ID is used to keep track of the distinction between the player who controls the ability that
     * started the dice roll and the player who does the rolling.
     * <p>
     * The only times this distinction matters is for Chaos Dragon and Ricochet.
     *
     * @param source
     * @param targetId      The player who is rolling the die
     * @param rollDieType
     * @param sides
     * @param rollsAmount
     */
    public RollDiceEvent(Ability source, UUID targetId, RollDieType rollDieType, int sides, int rollsAmount) {
        super(EventType.ROLL_DICE, targetId, source, source.getControllerId(), rollsAmount, false);
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
