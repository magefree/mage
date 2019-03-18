
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */


public class FilterControlledArtifactPermanent extends FilterControlledPermanent {

    public FilterControlledArtifactPermanent() {
        this("artifact you control");
    }

    public FilterControlledArtifactPermanent(String name) {
        super(name);
        this.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public FilterControlledArtifactPermanent(final FilterControlledArtifactPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledArtifactPermanent copy() {
        return new FilterControlledArtifactPermanent(this);
    }

}
