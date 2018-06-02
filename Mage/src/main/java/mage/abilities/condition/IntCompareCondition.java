
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public abstract class IntCompareCondition implements Condition {

    protected final ComparisonType type;
    protected final int value;

    public IntCompareCondition(ComparisonType type, int value) {
        this.type = type;
        this.value = value;
    }

    protected abstract int getInputValue(Game game, Ability source);

    @Override
    public final boolean apply(Game game, Ability source) {
        int inputValue = getInputValue(game, source);
        return ComparisonType.compare(inputValue , type, value);
    }

    @Override
    public String toString() {
        return type.toString() + value;
    }
}
