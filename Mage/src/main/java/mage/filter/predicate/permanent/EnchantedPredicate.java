
package mage.filter.predicate.permanent;

import java.util.Objects;
import java.util.UUID;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class EnchantedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(MageObject::isEnchantment);
    }

    @Override
    public String toString() {
        return "Enchanted" ;
    }
}
