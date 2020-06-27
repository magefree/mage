package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

public class AffinityEffect extends CostModificationEffectImpl {

    private final FilterControlledPermanent filter;

    public AffinityEffect(FilterControlledPermanent affinityFilter) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = affinityFilter;
        staticText = "Affinity for " + filter.getMessage();
    }

    public AffinityEffect(final AffinityEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // abilityToModify.getControllerId() works with Sen Triplets and in multiplayer games, see https://github.com/magefree/mage/issues/5931
        int count = game.getBattlefield().getActivePermanents(filter, abilityToModify.getControllerId(), source.getId(), game).size();
        CardUtil.reduceCost(abilityToModify, count);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public AffinityEffect copy() {
        return new AffinityEffect(this);
    }
}
