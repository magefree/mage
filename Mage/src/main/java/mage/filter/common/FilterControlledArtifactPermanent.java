
package mage.filter.common;

import mage.constants.CardType;

/**
 * @author LevelX2
 */


public class FilterControlledArtifactPermanent extends FilterControlledPermanent {

    public FilterControlledArtifactPermanent() {
        this("artifact you control");
    }

    public FilterControlledArtifactPermanent(String name) {
        super(name);
        this.add(CardType.ARTIFACT.getPredicate());
    }

    protected FilterControlledArtifactPermanent(final FilterControlledArtifactPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledArtifactPermanent copy() {
        return new FilterControlledArtifactPermanent(this);
    }

}
