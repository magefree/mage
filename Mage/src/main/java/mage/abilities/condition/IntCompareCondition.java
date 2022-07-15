
package mage.abilities.condition;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.game.Game;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        IntCompareCondition that = (IntCompareCondition) obj;

        return this.value == that.value
                && this.type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
