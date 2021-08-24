package mage.game.events;

import mage.abilities.Ability;

/**
 * @author TheElk801
 */
public class DieRolledEvent extends GameEvent {

    // 706.2.
    // After the roll, the number indicated on the top face of the die before any modifiers is
    // the natural result. The instruction may include modifiers to the roll which add to or
    // subtract from the natural result. Modifiers may also come from other sources. After
    // considering all applicable modifiers, the final number is the result of the die roll.

    private final int sides;
    private final int naturalResult;

    public DieRolledEvent(int sides, int naturalResult, int modifier, Ability source) {
        super(EventType.DIE_ROLLED, source.getControllerId(), source, source.getControllerId(), naturalResult + modifier, false);
        this.sides = sides;
        this.naturalResult = naturalResult;
    }

    public int getSides() {
        return sides;
    }

    public int getResult() {
        return amount;
    }

    public int getNaturalResult() {
        return naturalResult;
    }
}
