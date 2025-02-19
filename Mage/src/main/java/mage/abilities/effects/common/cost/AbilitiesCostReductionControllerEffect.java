package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.game.Game;

public class AbilitiesCostReductionControllerEffect extends AbilitiesCostReductionEffect {

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName) {
        super(activatedAbility, activatedAbilityName, 1);
    }

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount) {
        super(activatedAbility, activatedAbilityName, amount, false);
    }

    public AbilitiesCostReductionControllerEffect(Class<? extends ActivatedAbility> activatedAbility, String activatedAbilityName, int amount, boolean excludeSource) {
        super(activatedAbility, activatedAbilityName, amount, excludeSource);
    }

    public AbilitiesCostReductionControllerEffect(final AbilitiesCostReductionControllerEffect effect) {
        super(effect);
    }

    @Override
    protected void createStaticText(String activatedAbilityName) {
        staticText = activatedAbilityName + " costs you pay cost {" + amount + "} less";
    }

    @Override
    protected boolean checkAbilityCondition(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId());
    }

    @Override
    public AbilitiesCostReductionControllerEffect copy() {
        return new AbilitiesCostReductionControllerEffect(this);
    }
}