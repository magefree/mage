package mage.filter.predicate.permanent;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author duncant
 */
public class CanBeEnchantedByPredicate implements Predicate<Permanent> {

    private final MageObject auraEnchantment;

    public CanBeEnchantedByPredicate(MageObject auraEnchantment) {
        this.auraEnchantment = auraEnchantment;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return !input.cantBeAttachedBy(auraEnchantment, null, game, true);
    }

    @Override
    public String toString() {
        return "CanBeEnchanted(" + auraEnchantment.toString() + ')';
    }
}
