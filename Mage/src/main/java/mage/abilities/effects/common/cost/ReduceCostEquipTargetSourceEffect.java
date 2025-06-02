package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Collection;

/**
 * @author TheElk801
 */
public class ReduceCostEquipTargetSourceEffect extends CostModificationEffectImpl {

    private final int amount;

    public ReduceCostEquipTargetSourceEffect(int amount) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = amount;
        staticText = "equip abilities you activate that target {this} cost {" + amount + "} less to activate";
    }

    private ReduceCostEquipTargetSourceEffect(final ReduceCostEquipTargetSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof EquipAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        if (game != null && game.inCheckPlayableState()) {
            return !abilityToModify
                    .getTargets()
                    .isEmpty()
                    && abilityToModify
                    .getTargets()
                    .get(0)
                    .canTarget(source.getSourceId(), abilityToModify, game);
        }
        return abilityToModify
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(source.getSourceId()::equals);
    }

    @Override
    public ReduceCostEquipTargetSourceEffect copy() {
        return new ReduceCostEquipTargetSourceEffect(this);
    }
}
