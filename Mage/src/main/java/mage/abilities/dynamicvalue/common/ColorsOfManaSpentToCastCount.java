package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

import java.io.ObjectStreamException;

/**
 * @author LevelX2
 */
public enum ColorsOfManaSpentToCastCount implements DynamicValue {
    instance;

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Spell spell = game.getStack().getSpell(sourceAbility.getSourceId());
        if (spell == null) {
            MageObject mageObject = game.getLastKnownInformation(sourceAbility.getSourceId(), Zone.STACK);
            if (mageObject instanceof Spell) {
                spell = (Spell) mageObject;
            }
        }
        if (spell != null) {
            // NOT the cmc of the spell on the stack
            Mana mana = spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay();
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
        return count;
    }

    @Override
    public ColorsOfManaSpentToCastCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of colors of mana spent to cast this spell";
    }

}
