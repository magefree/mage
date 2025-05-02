package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility {

    public AffinityForArtifactsAbility() {
        super(Zone.ALL, new AffinityEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        setRuleAtTheTop(true);

        this.addHint(ArtifactYouControlHint.instance);
    }

    protected AffinityForArtifactsAbility(final AffinityForArtifactsAbility ability) {
        super(ability);
    }

    @Override
    public AffinityForArtifactsAbility copy() {
        return new AffinityForArtifactsAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for artifacts <i>(This spell costs {1} less to cast for each artifact you control.)</i>";
    }
}
