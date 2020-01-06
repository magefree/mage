
package mage.filter.common;

import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public class FilterArtifactCreaturePermanent extends FilterCreaturePermanent {

    public FilterArtifactCreaturePermanent() {
        this("artifact creature");
    }

    public FilterArtifactCreaturePermanent(String name) {
        super(name);
        this.add(CardType.ARTIFACT.getPredicate());
    }

    public FilterArtifactCreaturePermanent(final FilterArtifactCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactCreaturePermanent copy() {
        return new FilterArtifactCreaturePermanent(this);
    }
}
