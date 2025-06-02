package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * 702.40. Affinity
 * 702.40a Affinity is a static ability that functions while the spell with affinity is on the stack.
 * “Affinity for [text]” means “This spell costs you {1} less to cast for each [text] you control.”
 * 702.40b If a spell has multiple instances of affinity, each of them applies.
 *
 * @author Loki, TheElk801
 */
public class AffinityAbility extends SimpleStaticAbility {

    private AffinityType affinityType;

    public AffinityAbility(AffinityType affinityType) {
        super(Zone.ALL, new AffinityEffect(affinityType.getFilter()));
        setRuleAtTheTop(true);
        this.addHint(affinityType.getHint());
        this.affinityType = affinityType;
    }

    protected AffinityAbility(final AffinityAbility ability) {
        super(ability);
        this.affinityType = ability.affinityType;
    }

    @Override
    public AffinityAbility copy() {
        return new AffinityAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for " + affinityType.getFilter().getMessage() +
                " <i>(This spell costs {1} less to cast for each " +
                affinityType.getSingularName() + " you control.)</i>";
    }
}

class AffinityEffect extends CostModificationEffectImpl {

    private final FilterControlledPermanent filter;

    public AffinityEffect(FilterControlledPermanent affinityFilter) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = affinityFilter;
    }

    protected AffinityEffect(final AffinityEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // abilityToModify.getControllerId() works with Sen Triplets and in multiplayer games, see https://github.com/magefree/mage/issues/5931
        CardUtil.reduceCost(
                abilityToModify, game.getBattlefield().count(
                        filter, abilityToModify.getControllerId(), source, game
                )
        );
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public AffinityEffect copy() {
        return new AffinityEffect(this);
    }
}
