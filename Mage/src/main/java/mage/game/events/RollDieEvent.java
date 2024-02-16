package mage.game.events;

import mage.abilities.Ability;
import mage.constants.RollDieType;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class RollDieEvent extends GameEvent {

    private final RollDieType rollDieType;
    private final int sides;

    private int resultModifier = 0;
    private int rollsAmount = 1; // rolls X times and choose result from it
    private int bigIdeaRollsAmount = 0; // rolls 2x and sum result

    /**
     * The target ID is used to keep track of the distinction between the player who controls the ability that
     * started the dice roll and the player who does the rolling.
     * <p>
     * The only times this distinction matters is for Chaos Dragon and Ricochet.
     *
     * @param source
     * @param targetId      The player rolling the die
     * @param rollDieType
     * @param sides
     */
    public RollDieEvent(Ability source, UUID targetId, RollDieType rollDieType, int sides) {
        super(EventType.ROLL_DIE, targetId, source, source.getControllerId());
        this.rollDieType = rollDieType;
        this.sides = sides;
    }

    public int getResultModifier() {
        return resultModifier;
    }

    public void incResultModifier(int modifier) {
        this.resultModifier = CardUtil.overflowInc(this.resultModifier, modifier);
    }

    public RollDieType getRollDieType() {
        return rollDieType;
    }

    public int getSides() {
        return sides;
    }

    public int getRollsAmount() {
        return rollsAmount;
    }

    public void doubleRollsAmount() {
        this.rollsAmount = CardUtil.overflowMultiply(this.rollsAmount, 2);
    }

    public int getBigIdeaRollsAmount() {
        return bigIdeaRollsAmount;
    }

    public void incBigIdeaRollsAmount() {
        this.bigIdeaRollsAmount = CardUtil.overflowInc(this.bigIdeaRollsAmount, 1);
    }
}
