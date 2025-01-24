package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaximumDynamicValue implements DynamicValue {

    private final List<DynamicValue> dynamicValues;

    /**
     * Creates a {@link DynamicValue} that finds the maximum of multiple
     * {@link DynamicValue}s.
     *
     * @param dynamicValues The dynamic values to add together.
     */
    public MaximumDynamicValue(DynamicValue... dynamicValues) {
        this.dynamicValues = Arrays.asList(dynamicValues);
    }

    /**
     * Creates a {@link DynamicValue} that finds the maximum of multiple
     * {@link DynamicValue}s.
     *
     * @param dynamicValues The dynamic values to add together.
     */
    public MaximumDynamicValue(List<DynamicValue> dynamicValues) {
        this.dynamicValues = dynamicValues;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return dynamicValues.stream().mapToInt(d -> d.calculate(game, sourceAbility, effect)).max().orElse(0);
    }

    @Override
    public MaximumDynamicValue copy() {
        return new MaximumDynamicValue(this.dynamicValues);
    }

    @Override
    public String getMessage() {
        return this.dynamicValues.stream().map(DynamicValue::getMessage).collect(Collectors.joining(" "));
    }
}
