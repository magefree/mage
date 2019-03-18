

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author ayratn
 */
public class FilterArtifactPermanent extends FilterPermanent {

    public FilterArtifactPermanent() {
        this("artifact");
    }

    public FilterArtifactPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public FilterArtifactPermanent(final FilterArtifactPermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactPermanent copy() {
        return new FilterArtifactPermanent(this);
    }
}
