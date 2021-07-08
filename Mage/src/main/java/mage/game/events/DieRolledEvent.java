package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class DieRolledEvent extends GameEvent {

    private final int sides;

    public DieRolledEvent(int sides, Ability source) {
        super(EventType.DIE_ROLLED, source.getControllerId(), source, source.getControllerId());
        this.sides = sides;
    }

    public int getSides() {
        return sides;
    }
}
