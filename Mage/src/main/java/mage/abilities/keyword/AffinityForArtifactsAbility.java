package mage.abilities.keyword;

import mage.constants.AffinityType;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends AffinityAbility {

    public AffinityForArtifactsAbility() {
        super(AffinityType.ARTIFACTS);
    }

    protected AffinityForArtifactsAbility(final AffinityForArtifactsAbility ability) {
        super(ability);
    }

    @Override
    public AffinityForArtifactsAbility copy() {
        return new AffinityForArtifactsAbility(this);
    }
}
