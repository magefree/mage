package mage.abilities.keyword;

import mage.abilities.costs.Cost;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;

/**
 * @author TheElk801
 */
public class ArtifactLandcyclingAbility extends CyclingAbility {

    private static final FilterLandCard filter = new FilterLandCard("artifact land card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArtifactLandcyclingAbility(Cost cost) {
        super(cost, filter, "Artifact landcycling");
    }

    private ArtifactLandcyclingAbility(final ArtifactLandcyclingAbility ability) {
        super(ability);
    }

    @Override
    public ArtifactLandcyclingAbility copy() {
        return new ArtifactLandcyclingAbility(this);
    }
}
