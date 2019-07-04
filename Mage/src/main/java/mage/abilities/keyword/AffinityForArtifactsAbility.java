package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.common.ArtifactsYouControlHint;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility {

    public AffinityForArtifactsAbility() {
        super(Zone.ALL, new AffinityEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        setRuleAtTheTop(true);

        this.addHint(ArtifactsYouControlHint.instance);
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
        return "affinity for artifacts <i>(This spell costs {1} less to cast for each artifact you control.)</i>";
    }
}
