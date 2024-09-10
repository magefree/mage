
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 * @author TheElk801
 */
public class FilterArtifactOrEnchantmentCard extends FilterCard {

    public FilterArtifactOrEnchantmentCard() {
        this("artifact or enchantment card");
    }

    public FilterArtifactOrEnchantmentCard(String name) {
        super(name);
        this.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    protected FilterArtifactOrEnchantmentCard(final FilterArtifactOrEnchantmentCard filter) {
        super(filter);
    }

    @Override
    public FilterArtifactOrEnchantmentCard copy() {
        return new FilterArtifactOrEnchantmentCard(this);
    }
}