
package mage.abilities.mana.builder;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(this.mana, ((ConditionalManaBuilder) obj).mana);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mana);
    }
}
