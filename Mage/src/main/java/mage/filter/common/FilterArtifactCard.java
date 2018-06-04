

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author nantuko
 */
public class FilterArtifactCard extends FilterCard {

    public FilterArtifactCard() {
        this("artifact card");
    }

    public FilterArtifactCard(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public FilterArtifactCard(final FilterArtifactCard filter) {
        super(filter);
    }

    @Override
    public FilterArtifactCard copy() {
        return new FilterArtifactCard(this);
    }
}
