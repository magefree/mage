
package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author LevelX2
 */
public enum ManaSpentToCastCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (spell == null) {
            MageObject mageObject = game.getLastKnownInformation(source.getSourceId(), Zone.STACK);
            if (mageObject instanceof Spell) {
                spell = (Spell) mageObject;
            }
        }
        if (spell != null) {
            // NOT the cmc of the spell on the stack
            return spell.getSpellAbility().getManaCostsToPay().convertedManaCost();
        }
        return 0;
    }

    @Override
    public ManaSpentToCastCount copy() {
        return ManaSpentToCastCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the amount of mana spent to cast it";
    }

}
