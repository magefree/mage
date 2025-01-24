package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates a {@link DynamicValue} that finds the maximum of multiple
 * {@link DynamicValue}s.
 */
public class MaximumDynamicValue implements DynamicValue {

    private final List<DynamicValue> dynamicValues;

    public MaximumDynamicValue(DynamicValue... dynamicValues) {
        this(Arrays.asList(dynamicValues));
    }

    public MaximumDynamicValue(List<DynamicValue> dynamicValues) {
        this.dynamicValues = new ArrayList<>(dynamicValues);
    }

    private MaximumDynamicValue(final MaximumDynamicValue value) {
        this.dynamicValues = CardUtil.deepCopyObject(value.dynamicValues);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return dynamicValues.stream().mapToInt(d -> d.calculate(game, sourceAbility, effect)).max().orElse(0);
    }

    @Override
    public MaximumDynamicValue copy() {
        return new MaximumDynamicValue(this);
    }

    @Override
    public String getMessage() {
        return this.dynamicValues.stream().map(DynamicValue::getMessage).collect(Collectors.joining(" "));
    }
}
