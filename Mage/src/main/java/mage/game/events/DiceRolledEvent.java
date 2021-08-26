package mage.game.events;

import mage.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class DiceRolledEvent extends GameEvent {

    private final int sides;
    private final List<Object> results = new ArrayList<>(); // Integer for numerical and PlanarDieRollResult for planar

    public DiceRolledEvent(int sides, List<Object> results, Ability source) {
        super(EventType.DICE_ROLLED, source.getControllerId(), source, source.getControllerId());
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
