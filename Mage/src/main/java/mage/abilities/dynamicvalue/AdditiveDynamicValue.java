package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.constants.ValuePhrasing;
import mage.game.Game;
import mage.util.CardUtil;

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
    public String getMessage() {
        return this.dynamicValues.stream().map(DynamicValue::getMessage).collect(Collectors.joining(" "));
    }

    @Override
    public String getMessage(ValuePhrasing textPhrasing) {
        switch(textPhrasing){
            case X_HIDDEN:
                return "";
            case FOR_EACH:
                throw new IllegalArgumentException("FOR_EACH phrasing generation is not supported in AdditiveDynamicValue");
            default:
                return this.dynamicValues.stream().map(dv -> dv.getMessage(textPhrasing)).collect(Collectors.joining(" plus "));
        }
    }

    @Override
    public ValueHint getValueHint() {
        return new ValueHint(CardUtil.getTextWithFirstCharUpperCase(getMessage(ValuePhrasing.EQUAL_TO)), this);
    }
}
