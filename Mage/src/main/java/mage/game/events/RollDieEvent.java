package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class RollDieEvent extends GameEvent {

    private final int sides;
    private int modifier = 0;

    public RollDieEvent(int sides, Ability source) {
        super(EventType.ROLL_DIE, source.getControllerId(), source, source.getControllerId());
        this.sides = sides;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getSides() {
        return sides;
    }
}
