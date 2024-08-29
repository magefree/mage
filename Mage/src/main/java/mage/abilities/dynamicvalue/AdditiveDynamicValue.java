package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public AdditiveDynamicValue copy() {
        return new AdditiveDynamicValue(this.dynamicValues);
    }

    @Override
    public String getMessage(EffectPhrasing phrasing) {
        switch(phrasing){
            case X_HIDDEN:
                return "";
            case FOR_EACH:
                throw new IllegalArgumentException("FOR_EACH phrasing generation is not supported in AdditiveDynamicValue");
            case X_IS:
            case EQUAL_TO:
                return this.dynamicValues.stream().map(dv -> dv.getMessage(phrasing)).collect(Collectors.joining(" plus "));
            default:
                throw new IllegalArgumentException("enum " + phrasing + " is not supported in AdditiveDynamicValue");
        }
    }

    @Override
    public ValueHint getValueHint() {
        return new ValueHint("total", this);
    }
}
