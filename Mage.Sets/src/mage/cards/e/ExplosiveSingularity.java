package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveSingularity extends CardImpl {

    public ExplosiveSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");

        // As an additional cost to cast this spell, you may tap any number of untapped creatures you control. This spell costs {1} less to cast for each creature tapped this way.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES, false
        )).setText("you may tap any number of untapped creatures you control. " +
                "This spell costs {1} less to cast for each creature tapped this way"));
        Ability ability = new SimpleStaticAbility(Zone.ALL, new ExplosiveSingularityEffect());
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

class ExplosiveSingularityEffect extends CostModificationEffectImpl {

    ExplosiveSingularityEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private ExplosiveSingularityEffect(final ExplosiveSingularityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction;
        if (game.inCheckPlayableState()) {
            reduction = game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES,
                    source.getSourceId(), source.getControllerId(), game
            );
        } else {
            reduction = ((List<Permanent>) spellAbility.getEffects().get(0).getValue("tappedPermanents")).size();
        }
        CardUtil.adjustCost(spellAbility, reduction);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public ExplosiveSingularityEffect copy() {
        return new ExplosiveSingularityEffect(this);
    }
}
