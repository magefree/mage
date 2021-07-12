package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class DieRolledEvent extends GameEvent {

    private final int sides;
    private final int modifier;

    public DieRolledEvent(int sides, int amount, int modifier, Ability source) {
        super(EventType.DIE_ROLLED, source.getControllerId(), source, source.getControllerId(), amount, false);
        this.sides = sides;
        this.modifier = modifier;
    }

    public int getSides() {
        return sides;
    }

    public int getModifier() {
        return modifier;
    }
}
