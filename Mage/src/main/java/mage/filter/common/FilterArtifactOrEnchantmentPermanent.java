
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class FilterArtifactOrEnchantmentPermanent extends FilterPermanent {

    public FilterArtifactOrEnchantmentPermanent() {
        this("artifact or enchantment");
    }

    public FilterArtifactOrEnchantmentPermanent(String name) {
        super(name);
        this.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public FilterArtifactOrEnchantmentPermanent(final FilterArtifactOrEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactOrEnchantmentPermanent copy() {
        return new FilterArtifactOrEnchantmentPermanent(this);
    }
}