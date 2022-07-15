package mage.abilities.dynamicvalue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

public class AdditiveDynamicValue implements DynamicValue {

    private final List<DynamicValue> dynamicValues;

    /**
     * Creates a {@link DynamicValue} from that adds together multiple
     * {@link DynamicValue}s.
     *
     * @param dynamicValues The dynamic values to add together.
     */
    public AdditiveDynamicValue(DynamicValue... dynamicValues) {
        this.dynamicValues = Arrays.asList(dynamicValues);
    }

    /**
     * Creates a {@link DynamicValue} from that adds together multiple
     * {@link DynamicValue}s.
     *
     * @param dynamicValues The dynamic values to add together.
     */
    public AdditiveDynamicValue(List<DynamicValue> dynamicValues) {
        this.dynamicValues = dynamicValues;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return dynamicValues.stream().mapToInt(d -> d.calculate(game, sourceAbility, effect)).sum();
    }

    @Override
    public DynamicValue copy() {
        return new AdditiveDynamicValue(this.dynamicValues);
    }

    @Override
    public String getMessage() {
        return this.dynamicValues.stream().map(DynamicValue::getMessage).collect(Collectors.joining(" "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynamicValues);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AdditiveDynamicValue that = (AdditiveDynamicValue) obj;
        return Objects.equals(this.dynamicValues, that.dynamicValues);
    }
}
