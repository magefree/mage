
package mage.abilities.mana.conditional;

import mage.ConditionalMana;
import mage.Mana;

/**
 * @author noxx
 */
public class CreatureCastConditionalMana extends ConditionalMana {

    public CreatureCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell";
        addCondition(new CreatureCastManaCondition());
    }
}