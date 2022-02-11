package mage.game.events;

import mage.abilities.Ability;
import mage.constants.RollDieType;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class RollDieEvent extends GameEvent {

    private final RollDieType rollDieType;
    private final int sides;

    private int resultModifier = 0;
    private int rollsAmount = 1; // rolls X times and choose result from it
    private int bigIdeaRollsAmount = 0; // rolls 2x and sum result

    public RollDieEvent(Ability source, RollDieType rollDieType, int sides) {
        super(EventType.ROLL_DIE, source.getControllerId(), source, source.getControllerId());
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
