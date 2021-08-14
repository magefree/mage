package mage.game.events;

import mage.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class DiceRolledEvent extends GameEvent {

    private final int sides;
    private final List<Integer> results = new ArrayList<>();

    public DiceRolledEvent(int sides, List<Integer> results, Ability source) {
        super(EventType.DICE_ROLLED, source.getControllerId(), source, source.getControllerId());
        this.sides = sides;
        this.results.addAll(results);
    }

    public int getSides() {
        return sides;
    }

    public List<Integer> getResults() {
        return results;
    }
}
