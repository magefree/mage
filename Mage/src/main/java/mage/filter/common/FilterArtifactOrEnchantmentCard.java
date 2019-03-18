
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author TheElk801
 */
public class FilterArtifactOrEnchantmentCard extends FilterCard {

    public FilterArtifactOrEnchantmentCard() {
        this("artifact or enchantment card");
    }

    public FilterArtifactOrEnchantmentCard(String name) {
        super(name);
        this.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public FilterArtifactOrEnchantmentCard(final FilterArtifactOrEnchantmentCard filter) {
        super(filter);
    }

    @Override
    public FilterArtifactOrEnchantmentCard copy() {
        return new FilterArtifactOrEnchantmentCard(this);
    }
}