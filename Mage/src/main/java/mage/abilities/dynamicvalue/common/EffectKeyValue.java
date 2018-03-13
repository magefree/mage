package mage.abilities.dynamicvalue.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * @author stravant
 */
public class EffectKeyValue implements DynamicValue {
    private String key;
    private String description;

    public EffectKeyValue(String key) {
        this.key = key;
        this.description = key;
    }

    public EffectKeyValue(String key, String description) {
        this(key);
        this.description = description;
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        return (Integer)effect.getValue(key);
    }

    @Override
    public EffectKeyValue copy(){
        return new EffectKeyValue(this.key, this.description);
    }

    @Override
    public String toString() {
        return "equal to";
    }

    @Override
    public String getMessage() {
        return description;
    }
}