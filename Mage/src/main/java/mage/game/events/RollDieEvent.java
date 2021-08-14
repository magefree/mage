package mage.game.events;

import mage.abilities.Ability;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class RollDieEvent extends GameEvent {

    private final int sides;
    private int modifier = 0;
    private int multiplier = 1;
    private int bigIdea = 0;

    public RollDieEvent(int sides, Ability source) {
        super(EventType.ROLL_DIE, source.getControllerId(), source, source.getControllerId());
        this.sides = sides;
    }

    public void incrementModifier(int modifier) {
        this.modifier = CardUtil.overflowInc(this.modifier, modifier);
    }

    public int getModifier() {
        return modifier;
    }

    public int getSides() {
        return sides;
    }

    public void doubleMultiplier(int multiplier) {
        this.multiplier = CardUtil.overflowMultiply(this.multiplier, 2);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void incrementBigIdea() {
        this.bigIdea = CardUtil.overflowInc(this.bigIdea, 1);
    }

    public int getBigIdea() {
        return bigIdea;
    }
}
