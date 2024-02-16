package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapVariableTargetCost;

/**
 * @author TheElk801
 */
public final class ExplosiveSingularity extends CardImpl {

    public ExplosiveSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // As an additional cost to cast this spell, you may tap any number of untapped creatures you control. This spell costs {1} less to cast for each creature tapped this way.
        this.getSpellAbility().addCost(new TapVariableTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES).setText("you may tap any number of untapped creatures you control. "
                + "This spell costs {1} less to cast for each creature tapped this way"));
        Ability ability = new SimpleStaticAbility(Zone.ALL, new ExplosiveSingularityCostReductionEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability);

        // Explosive Singularity deals 10 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(10));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ExplosiveSingularity(final ExplosiveSingularity card) {
        super(card);
    }

    @Override
    public ExplosiveSingularity copy() {
        return new ExplosiveSingularity(this);
    }
}

class ExplosiveSingularityCostReductionEffect extends CostModificationEffectImpl {

    ExplosiveSingularityCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private ExplosiveSingularityCostReductionEffect(final ExplosiveSingularityCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction;
        for (Cost cost : spellAbility.getCosts()) {
            if (cost instanceof TapVariableTargetCost) {
                if (game.inCheckPlayableState()) {
                    // allows to cast in getPlayable
                    reduction = ((TapVariableTargetCost) cost).getMaxValue(source, game);
                    CardUtil.adjustCost(spellAbility, reduction);
                } else {
                    // real cast
                    reduction = ((TapVariableTargetCost) cost).getAmount();
                    CardUtil.adjustCost(spellAbility, reduction);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public ExplosiveSingularityCostReductionEffect copy() {
        return new ExplosiveSingularityCostReductionEffect(this);
    }
}
