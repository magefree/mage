package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

public abstract class MiracleCostModifier extends CostModificationEffectImpl {

    private SpellAbility castToModify;

    public MiracleCostModifier(Outcome outcome, CostModificationType type) {
        super(Duration.Custom, outcome, type);
    }

    protected MiracleCostModifier(MiracleCostModifier effect) {
        super(effect);
        this.castToModify = effect.castToModify;
    }

    public MiracleCostModifier setCastToModify(SpellAbility castToModify) {
        this.castToModify = castToModify;
        return this;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify != null && abilityToModify.getId().equals(this.castToModify.getId());
    }
}
