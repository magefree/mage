package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

public abstract class AbilitiesCostReductionEffect extends CostModificationEffectImpl {

    protected final Class<? extends ActivatedAbility> activatedAbility;
    protected final int amount;
    protected final boolean excludeSource;

    protected AbilitiesCostReductionEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount) {
        this(activatedAbility, activatedAbilityName, amount, false);
    }

    protected AbilitiesCostReductionEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount, boolean excludeSource) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.activatedAbility = activatedAbility;
        this.amount = amount;
        this.excludeSource = excludeSource;
        createStaticText(activatedAbilityName);
    }

    protected AbilitiesCostReductionEffect(final AbilitiesCostReductionEffect effect) {
        super(effect);
        this.activatedAbility = effect.activatedAbility;
        this.amount = effect.amount;
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (excludeSource && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return false;
        }

        CardUtil.reduceCost(abilityToModify, amount);
        return true;
    }

    protected abstract void createStaticText(String activatedAbilityName);
    protected abstract boolean checkAbilityCondition(Ability abilityToModify, Ability source, Game game);

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return activatedAbility.isInstance(abilityToModify) && checkAbilityCondition(abilityToModify, source, game);
    }
}