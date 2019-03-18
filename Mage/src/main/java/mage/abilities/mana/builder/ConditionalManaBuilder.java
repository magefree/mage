
package mage.abilities.mana.builder;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author noxx
 */
public abstract class ConditionalManaBuilder implements Builder<ConditionalMana> {

    protected Mana mana;

    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        this.mana = mana;
        return this;
    }

    public abstract String getRule();
}
