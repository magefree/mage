package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author JayDi85
 */
public class SpellCostReductionForEachSourceEffect extends CostModificationEffectImpl {

    private final DynamicValue eachAmount;
    private final ManaCosts<ManaCost> reduceManaCosts;
    private final int reduceGenericMana;

    public SpellCostReductionForEachSourceEffect(int reduceGenericMana, DynamicValue eachAmount) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.eachAmount = eachAmount;
        this.reduceManaCosts = null;
        this.reduceGenericMana = reduceGenericMana;

        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs {")
                .append(this.reduceGenericMana)
                .append("} less to cast for each ")
                .append(this.eachAmount.getMessage());
        this.staticText = sb.toString();
    }

    public SpellCostReductionForEachSourceEffect(ManaCosts<ManaCost> reduceManaCosts, DynamicValue eachAmount) {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.eachAmount = eachAmount;
        this.reduceManaCosts = reduceManaCosts;
        this.reduceGenericMana = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs ");
        sb.append(reduceManaCosts.getText());
        sb.append(" less to cast for each ").append(this.eachAmount.getMessage());
        this.staticText = sb.toString();
    }

    protected SpellCostReductionForEachSourceEffect(final SpellCostReductionForEachSourceEffect effect) {
        super(effect);
        this.eachAmount = effect.eachAmount;
        this.reduceManaCosts = effect.reduceManaCosts;
        this.reduceGenericMana = effect.reduceGenericMana;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int needReduceAmount = eachAmount.calculate(game, source, this);
        if (needReduceAmount > 0) {
            if (reduceManaCosts != null) {
                // color reduce
                ManaCosts<ManaCost> needReduceMana = new ManaCostsImpl<>();
                for (int i = 0; i < needReduceAmount; i++) {
                    needReduceMana.add(reduceManaCosts.copy());
                }
                CardUtil.adjustCost((SpellAbility) abilityToModify, needReduceMana, false);
            } else {
                // generic reduce
                CardUtil.reduceCost(abilityToModify, needReduceAmount * this.reduceGenericMana);
            }
        }

        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            return true;
        }
        return false;
    }

    @Override
    public SpellCostReductionForEachSourceEffect copy() {
        return new SpellCostReductionForEachSourceEffect(this);
    }
}
