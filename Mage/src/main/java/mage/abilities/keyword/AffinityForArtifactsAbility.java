
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.common.AffinityEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AffinityForArtifactsAbility() {
        super(Zone.OUTSIDE, new AffinityEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        setRuleAtTheTop(true);
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

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int count = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ability.getControllerId(), game).size();
            if (count > 0) {
                CardUtil.adjustCost((SpellAbility)ability, count);
            }
        }
    }
}
