package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class AbilitiesCostReductionControllerEffect extends CostModificationEffectImpl {

    private final Class<? extends ActivatedAbility> activatedAbility;
    private final int amount;
    private final boolean excludeSource;

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName) {
        this(activatedAbility, activatedAbilityName, 1);
    }

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount) {
        this(activatedAbility, activatedAbilityName, amount, false);
    }

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount, boolean excludeSource) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.activatedAbility = activatedAbility;
        staticText = activatedAbilityName + " costs you pay cost {" + amount + "} less";
        this.amount = amount;
        this.excludeSource = excludeSource;
    }

    public AbilitiesCostReductionControllerEffect(AbilitiesCostReductionControllerEffect effect) {
        super(effect);
        this.activatedAbility = effect.activatedAbility;
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
                && activatedAbility.isInstance(abilityToModify);
    }
}
