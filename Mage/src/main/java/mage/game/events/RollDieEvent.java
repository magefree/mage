package mage.game.events;

import mage.abilities.Ability;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class RollDieEvent extends GameEvent {

    private final int sides;
    private int resultModifier = 0;
    private int rollsAmount = 1;
    private int bigIdea = 0; // rolls 2x + sum result

    public RollDieEvent(int sides, Ability source) {
        super(EventType.ROLL_DIE, source.getControllerId(), source, source.getControllerId());
        this.sides = sides;
    }

    public int getResultModifier() {
        return resultModifier;
    }

    public void incResultModifier(int modifier) {
        this.resultModifier = CardUtil.overflowInc(this.resultModifier, modifier);
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

    public int getBigIdea() {
        return bigIdea;
    }

    public void incBigIdea() {
        this.bigIdea = CardUtil.overflowInc(this.bigIdea, 1);
    }
}
