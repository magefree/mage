
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author LevelX2
 */
public class FilterArtifactOrEnchantmentPermanent extends FilterPermanent {

    public FilterArtifactOrEnchantmentPermanent() {
        this("artifact or enchantment");
    }

    public FilterArtifactOrEnchantmentPermanent(String name) {
        super(name);
        this.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    protected FilterArtifactOrEnchantmentPermanent(final FilterArtifactOrEnchantmentPermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactOrEnchantmentPermanent copy() {
        return new FilterArtifactOrEnchantmentPermanent(this);
    }
}