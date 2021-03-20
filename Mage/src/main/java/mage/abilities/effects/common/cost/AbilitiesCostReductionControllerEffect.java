package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author Styxo
 */
public class AbilitiesCostReductionControllerEffect extends CostModificationEffectImpl {

    private final Class activatedAbility;

    public AbilitiesCostReductionControllerEffect(Class activatedAbility, String activatedAbilityName) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.activatedAbility = activatedAbility;
        staticText = activatedAbilityName + " costs you pay cost {1} less";
    }

    public AbilitiesCostReductionControllerEffect(AbilitiesCostReductionControllerEffect effect) {
        super(effect);
        this.activatedAbility = effect.activatedAbility;
    }

    @Override
    public AbilitiesCostReductionControllerEffect copy() {
        return new AbilitiesCostReductionControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && activatedAbility.isInstance(abilityToModify);
    }
}
