package mage.abilities.dynamicvalue.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author stravant
 */
public class EffectKeyValue implements DynamicValue {

    private final String key;
    private final String description;

    public EffectKeyValue(String key) {
        this(key, key);
    }

    public EffectKeyValue(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue(key);
    }

    @Override
    public EffectKeyValue copy() {
        return new EffectKeyValue(this.key, this.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
