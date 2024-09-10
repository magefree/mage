

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;


/**
 * @author ayratn
 */
public class FilterArtifactPermanent extends FilterPermanent {

    public FilterArtifactPermanent() {
        this("artifact");
    }

    public FilterArtifactPermanent(String name) {
        super(name);
        this.add(CardType.ARTIFACT.getPredicate());
    }

    protected FilterArtifactPermanent(final FilterArtifactPermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactPermanent copy() {
        return new FilterArtifactPermanent(this);
    }
}
