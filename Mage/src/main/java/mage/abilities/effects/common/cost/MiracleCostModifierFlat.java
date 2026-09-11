package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CostModificationType;
import mage.constants.Outcome;
import mage.game.Game;

public class MiracleCostModifierFlat extends MiracleCostModifier {

    final private String costs;

    public MiracleCostModifierFlat(String costs) {
        super(Outcome.Benefit, CostModificationType.SET_COST);
        this.costs = costs;
    }

    private MiracleCostModifierFlat(MiracleCostModifierFlat effect) {
        super(effect);
        this.costs = effect.costs;
    }

    @Override
    public MiracleCostModifierFlat copy() {
        return new MiracleCostModifierFlat(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        final ManaCosts<ManaCost> costRef = abilityToModify.getManaCostsToPay();
        // replace with the new cost
        costRef.clear();
        costRef.add(new ManaCostsImpl<>(this.costs));
        return true;
    }
}
