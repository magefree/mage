package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

public class SpellCostReductionSourceForOpponentsEffect extends CostModificationEffectImpl {

    public SpellCostReductionSourceForOpponentsEffect() {
        this("undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>");
    }

    public SpellCostReductionSourceForOpponentsEffect(String newStaticText) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = newStaticText;
    }

    public SpellCostReductionSourceForOpponentsEffect(final SpellCostReductionSourceForOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int count = game.getOpponents(source.getControllerId()).size();
        CardUtil.reduceCost(abilityToModify, count);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public SpellCostReductionSourceForOpponentsEffect copy() {
        return new SpellCostReductionSourceForOpponentsEffect(this);
    }
}
