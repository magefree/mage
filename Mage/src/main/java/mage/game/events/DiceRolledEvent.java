package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class DiceRolledEvent extends GameEvent {

    private final int sides;

    public DiceRolledEvent(int sides, int amount, Ability source) {
        super(EventType.DICE_ROLLED, source.getControllerId(), source, source.getControllerId(), amount, false);
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }
}
