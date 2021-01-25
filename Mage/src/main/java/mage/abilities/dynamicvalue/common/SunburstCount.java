
package mage.abilities.dynamicvalue.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 * @author Nicolas
 */
public enum SunburstCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        if (!game.getStack().isEmpty()) {
            StackObject spell = game.getStack().getFirst();
            if (spell instanceof Spell && ((Spell) spell).getSourceId().equals(sourceAbility.getSourceId())) {
                Mana mana = ((Spell) spell).getSpellAbility().getManaCostsToPay().getUsedManaToPay();
                if (mana.getBlack() > 0) {
                    count++;
                }
                if (mana.getBlue() > 0) {
                    count++;
                }
                if (mana.getGreen() > 0) {
                    count++;
                }
                if (mana.getRed() > 0) {
                    count++;
                }
                if (mana.getWhite() > 0) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public SunburstCount copy() {
        return SunburstCount.instance;
    }

    @Override
    public String getMessage() {
        return "color of mana spent to cast it";
    }

}
