package mage.game.events;

import mage.abilities.Ability;
import mage.constants.PlanarDieRollResult;
import mage.constants.RollDieType;

import java.util.UUID;

/**
 * @author TheElk801, JayDi85
 */
public class DieRolledEvent extends GameEvent {

    // 706.2.
    // After the roll, the number indicated on the top face of the die before any modifiers is
    // the natural result. The instruction may include modifiers to the roll which add to or
    // subtract from the natural result. Modifiers may also come from other sources. After
    // considering all applicable modifiers, the final number is the result of the die roll.

    private final RollDieType rollDieType;
    private final int sides;
    private final int naturalResult; // planar die returns 0 values in result and natural result
    private final PlanarDieRollResult planarResult;

    /**
     * The target ID is used to keep track of the distinction between the player who controls the ability that
     * started the dice roll and the player who does the rolling.
     * <p>
     * The only times this distinction matters is for Chaos Dragon and Ricochet.
     *
     * @param source  The ability causing the die roll
     * @param targetId  The player who rolled the die
     * @param rollDieType
     * @param sides
     * @param naturalResult  the result of the die roll before any modifiers
     * @param modifier  the sum of all modifiers
     * @param planarResult
     */
    public DieRolledEvent(Ability source, UUID targetId, RollDieType rollDieType, int sides, int naturalResult, int modifier, PlanarDieRollResult planarResult) {
        super(EventType.DIE_ROLLED, targetId, source, source.getControllerId(), naturalResult + modifier, false);
        this.rollDieType = rollDieType;
        this.sides = sides;
        this.naturalResult = naturalResult;
        this.planarResult = planarResult;
    }

    public RollDieType getRollDieType() {
        return rollDieType;
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

    public PlanarDieRollResult getPlanarResult() {
        return planarResult;
    }
}
