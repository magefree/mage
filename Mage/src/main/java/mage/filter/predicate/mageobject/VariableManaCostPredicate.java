
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * Spell has a X ManaCost
 *
 * @author LevelX2
 */
public class VariableManaCostPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        for (ManaCost manaCost : input.getManaCost()) {
            if (manaCost instanceof VariableManaCost) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "VariableManaCost";
    }
}
