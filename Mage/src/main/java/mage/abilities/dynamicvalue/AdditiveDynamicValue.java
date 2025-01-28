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
 * Creates a {@link DynamicValue} from that adds together multiple
 * {@link DynamicValue}s.
 */
public class AdditiveDynamicValue implements DynamicValue {

    private final List<DynamicValue> dynamicValues;

    public AdditiveDynamicValue(DynamicValue... dynamicValues) {
        this(Arrays.asList(dynamicValues));
    }

    public AdditiveDynamicValue(List<DynamicValue> dynamicValues) {
        this.dynamicValues = new ArrayList<>(dynamicValues);
    }

    private AdditiveDynamicValue(final AdditiveDynamicValue value) {
        this.dynamicValues = CardUtil.deepCopyObject(value.dynamicValues);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return dynamicValues.stream().mapToInt(d -> d.calculate(game, sourceAbility, effect)).sum();
    }

    @Override
    public AdditiveDynamicValue copy() {
        return new AdditiveDynamicValue(this);
    }

    @Override
    public String getMessage() {
        return this.dynamicValues.stream().map(DynamicValue::getMessage).collect(Collectors.joining(" "));
    }
}
