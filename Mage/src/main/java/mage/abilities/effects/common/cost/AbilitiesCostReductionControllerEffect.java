package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class AbilitiesCostReductionControllerEffect extends CostModificationEffectImpl {

    private final FilterAbility filter;
    private final int amount;
    private final boolean excludeSource;

    public AbilitiesCostReductionControllerEffect(FilterAbility filter) {
        this(filter, 1);
    }

    public AbilitiesCostReductionControllerEffect(FilterAbility filter, int amount) {
        this(filter, amount, false);
    }

    public AbilitiesCostReductionControllerEffect(FilterAbility filter, int amount, boolean excludeSource) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        staticText = filter.getMessage() + " costs you pay cost {" + amount + "} less";
        this.amount = amount;
        this.excludeSource = excludeSource;
    }

    public AbilitiesCostReductionControllerEffect(AbilitiesCostReductionControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public AbilitiesCostReductionControllerEffect copy() {
        return new AbilitiesCostReductionControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {

        if (excludeSource && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        
        CardUtil.reduceCost(abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && filter.match(abilityToModify, game);
    }
}
