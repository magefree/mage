package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.CostModificationEffect;
import mage.abilities.effects.common.AffinityEffect;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {
    private static FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.getCardType().add(Constants.CardType.ARTIFACT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public AffinityForArtifactsAbility() {
        super(Constants.Zone.OUTSIDE, new AffinityEffect(filter));
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

    @Override
    public void adjustCosts(Ability ability, Game game) {
        ((CostModificationEffect)getEffects().get(0)).apply(game, this, ability);
    }
}
