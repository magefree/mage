
package mage.abilities.mana.conditional;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.game.Game;

/**
 * @author noxx
 */
public abstract class ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    public abstract boolean apply(Game game, Ability source, UUID originalId, Cost costToPay);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
