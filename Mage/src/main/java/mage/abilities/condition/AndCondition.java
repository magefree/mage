package mage.abilities.condition;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Combines conditions to one compound conditon, all conditions must be
 * true to return true for the compound condtion.
 *
 * @author 18ths
 */
public class AndCondition implements Condition {

    private final ArrayList<Condition> conditions = new ArrayList<>();
    private final String text;

    public AndCondition(Condition... conditions) {
        this("", conditions);
    }

    public AndCondition(String text, Condition... conditions) {
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
}
