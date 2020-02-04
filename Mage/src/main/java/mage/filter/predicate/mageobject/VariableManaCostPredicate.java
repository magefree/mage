
package mage.filter.predicate.mageobject;

import mage.MageObject;
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
        return input.getManaCost().stream().anyMatch(manaCost -> manaCost instanceof VariableManaCost);

    }

    @Override
    public String toString() {
        return "VariableManaCost";
    }
}
