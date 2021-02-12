package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class SpellCostReductionSourceEffect extends CostModificationEffectImpl {

    private final DynamicValue amount;
    private ManaCosts<ManaCost> manaCostsToReduce = null;
    private Condition condition;

    public SpellCostReductionSourceEffect(ManaCosts<ManaCost> manaCostsToReduce) {
        this(manaCostsToReduce, null);
    }

    public SpellCostReductionSourceEffect(ManaCosts<ManaCost> manaCostsToReduce, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = StaticValue.get(0);
        this.manaCostsToReduce = manaCostsToReduce;
        this.condition = condition;

        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs ");
        sb.append(manaCostsToReduce.getText());
        sb.append(" less to cast");
        if (this.condition != null) {
            sb.append(" if ").append(this.condition.toString());
        }
        this.staticText = sb.toString();
    }

    public SpellCostReductionSourceEffect(int amount) {
        this(StaticValue.get(amount), null);
    }

    public SpellCostReductionSourceEffect(DynamicValue amount) {
        this(amount, null);
    }

    public SpellCostReductionSourceEffect(int amount, Condition condition) {
        this(StaticValue.get(amount), condition);
    }

    public SpellCostReductionSourceEffect(DynamicValue amount, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = amount;
        this.condition = condition;
        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs {").append(this.amount).append("} less to cast");
        if (this.condition != null) {
            sb.append(" ").append(this.condition.toString().startsWith("if ") ? "" : "if ");
            sb.append(this.condition.toString());
        }
        if (this.amount.toString().equals("X")) {
            sb.append(", where X is ").append(this.amount.getMessage());
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
            CardUtil.reduceCost(abilityToModify, this.amount.calculate(game, source, this));
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            // some conditions can works after put on stack, so skip it in get playable (allows user to put card on stack anyway)
            boolean skipCondition = game.inCheckPlayableState() && canWorksOnStackOnly();
            return condition == null || skipCondition || condition.apply(game, source);
        }
        return false;
    }

    @Override
    public SpellCostReductionSourceEffect copy() {
        return new SpellCostReductionSourceEffect(this);
    }
}
