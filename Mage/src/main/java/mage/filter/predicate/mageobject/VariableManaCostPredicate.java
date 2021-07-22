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
public enum VariableManaCostPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getManaCost().stream().anyMatch(VariableManaCost.class::isInstance);
    }

    @Override
    public String toString() {
        return "VariableManaCost";
    }
}
