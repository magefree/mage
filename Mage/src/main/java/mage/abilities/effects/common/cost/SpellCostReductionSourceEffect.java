package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class SpellCostReductionSourceEffect extends CostModificationEffectImpl {

    private final int amount;
    private ManaCosts<ManaCost> manaCostsToReduce = null;
    private Condition condition;

    public SpellCostReductionSourceEffect(ManaCosts<ManaCost> manaCostsToReduce) {
        this(manaCostsToReduce, null);
    }

    public SpellCostReductionSourceEffect(ManaCosts<ManaCost> manaCostsToReduce, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = 0;
        this.manaCostsToReduce = manaCostsToReduce;
        this.condition = condition;

        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs ");
        for (String manaSymbol : manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" less");
        if (this.condition != null) {
            sb.append(" to if ").append(this.condition.toString());
        }

        this.staticText = sb.toString();
    }

    public SpellCostReductionSourceEffect(int amount) {
        this(amount, null);
    }

    public SpellCostReductionSourceEffect(int amount, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = amount;
        this.condition = condition;
        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs {").append(amount).append("} less to cast");
        if (this.condition != null) {
            sb.append(" ").append(this.condition.toString().startsWith("if ") ? "" : "if ");
            sb.append(this.condition.toString());
        }
        this.staticText = sb.toString();
    }

    protected SpellCostReductionSourceEffect(final SpellCostReductionSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToReduce != null) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce, false);
        } else {
            CardUtil.reduceCost(abilityToModify, this.amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            return condition == null || condition.apply(game, source);
        }
        return false;
    }

    @Override
    public SpellCostReductionSourceEffect copy() {
        return new SpellCostReductionSourceEffect(this);
    }
}
