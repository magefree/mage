
package mage.abilities.mana.conditional;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.game.Game;

/**
 * Condition of conditional mana ("Spend this mana only to ...").
 * <p>
 * Conditions are compared by value: the same class means the same restriction, so mana with the same restriction
 * from different sources can be counted together in the available mana calculation. A condition with its own
 * settings (fields) must override equals and hashCode with them.
 *
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
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
