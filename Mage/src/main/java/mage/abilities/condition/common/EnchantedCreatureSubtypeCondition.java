
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 *
 * @author fireshoes
 */
public class EnchantedCreatureSubtypeCondition implements Condition {

    private final FilterPermanent filter = new FilterCreaturePermanent();

    public EnchantedCreatureSubtypeCondition(SubType string) {
        filter.add(string.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (filter.match(creature, enchantment.getControllerId(), source, game)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        EnchantedCreatureSubtypeCondition that = (EnchantedCreatureSubtypeCondition) obj;
        return Objects.equals(this.filter, that.filter);
    }
}
