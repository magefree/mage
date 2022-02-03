
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Addictiveme
 */
public class FilterArtifactOrCreaturePermanent extends FilterPermanent {

    public FilterArtifactOrCreaturePermanent() {
        this("artifact or creature");
    }

    public FilterArtifactOrCreaturePermanent(String name) {
        super(name);
        this.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public FilterArtifactOrCreaturePermanent(final FilterArtifactOrCreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterArtifactOrCreaturePermanent copy() {
        return new FilterArtifactOrCreaturePermanent(this);
    }
}