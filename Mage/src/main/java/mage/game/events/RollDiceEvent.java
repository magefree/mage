package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class RollDiceEvent extends GameEvent {

    private final int sides;
    private int toIgnore = 0;

    public RollDiceEvent(int sides, int amount, Ability source) {
        super(EventType.ROLL_DICE, source.getControllerId(), source, source.getControllerId(), amount, false);
        this.sides = sides;
        this.amount = amount;
    }

    public int getSides() {
        return sides;
    }

    public void setToIgnore(int toIgnore) {
        this.toIgnore = toIgnore;
    }

    public int getToIgnore() {
        return toIgnore;
    }
}
