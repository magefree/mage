
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;
import sun.font.CreatedFontTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Combines conditions to one compound conditon, all single conditons must be
 * true to return true for the compound condtion.
 *
 * @author LevelX2
 */
public class CompoundCondition implements Condition {

    private final ArrayList<Condition> conditions = new ArrayList<>();

    private final String text;

    public CompoundCondition(Condition... conditions) {
        this("", conditions);
    }

    public CompoundCondition(String text, Condition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return conditions.stream().allMatch(condition -> condition.apply(game, source));
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        CompoundCondition that = (CompoundCondition) obj;

        return Objects.equals(this.text, that.text) && Objects.equals(this.conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditions, text);
    }
}
