package mage.game.events;

import mage.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class DiceRolledEvent extends GameEvent {

    private final int sides;
    private final List<Object> results = new ArrayList<>(); // Integer for numerical and PlanarDieRollResult for planar

    /**
     * The target ID is used to keep track of the distinction between the player who controls the ability that
     * started the dice roll and the player who does the rolling.
     * <p>
     * The only times this distinction matters is for Chaos Dragon and Ricochet.
     *
     * @param sides
     * @param results
     * @param source
     * @param targetId  The player who rolled the die
     */
    public DiceRolledEvent(int sides, List<Object> results, Ability source, UUID targetId) {
        super(EventType.DICE_ROLLED, targetId, source, source.getControllerId());
        this.sides = sides;
        this.results.addAll(results);
    }

    public int getSides() {
        return sides;
    }

    public List<Object> getResults() {
        return results;
    }
}
