package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AffinityEffect;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility {
    private static FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.getCardType().add(Constants.CardType.ARTIFACT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public AffinityForArtifactsAbility() {
        super(Constants.Zone.ALL, new AffinityEffect(filter));
    }

    public AffinityForArtifactsAbility(final AffinityForArtifactsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForArtifactsAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for artifacts";
    }
}
