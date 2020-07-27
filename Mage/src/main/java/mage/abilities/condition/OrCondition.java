
package mage.abilities.condition;

import java.util.ArrayList;
import java.util.Arrays;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * Combines conditions to one compound conditon, one condition must be
 * true to return true for the compound condtion.
 *
 * @author emerald000
 */
public class OrCondition implements Condition {

    private final ArrayList<Condition> conditions = new ArrayList<>();
    private final String text;

    public OrCondition(Condition... conditions) {
        this("", conditions);
    }

    public OrCondition(String text, Condition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return conditions.stream().anyMatch(condition -> condition.apply(game, source));
    }

    @Override
    public String toString() {
        return text;
    }
}
