
package mage.filter.predicate.other;

import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * @author duncant
 */
public class FromSetPredicate<T extends MageItem> implements Predicate<T> {
    
    protected final Set<UUID> set;

    public FromSetPredicate(Set<UUID> set) {
        this.set = set;
    }

    @Override
    public boolean apply(T input, Game game) {
        return set.contains(input.getId());
    }
}
