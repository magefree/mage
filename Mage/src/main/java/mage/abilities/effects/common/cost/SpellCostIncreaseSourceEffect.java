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
public class SpellCostIncreaseSourceEffect extends CostModificationEffectImpl {

    private final DynamicValue amount;
    private ManaCosts<ManaCost> manaCostsToIncrease = null;
    private Condition condition;

    public SpellCostIncreaseSourceEffect(ManaCosts<ManaCost> manaCostsToIncrease) {
        this(manaCostsToIncrease, null);
    }

    public SpellCostIncreaseSourceEffect(ManaCosts<ManaCost> manaCostsToIncrease, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.amount = StaticValue.get(0);
        this.manaCostsToIncrease = manaCostsToIncrease;
        this.condition = condition;

        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs ");
        sb.append(manaCostsToIncrease.getText());
        sb.append(" more to cast");
        if (this.condition != null) {
            sb.append(" if ").append(this.condition.toString());
        }
        this.staticText = sb.toString();
    }

    public SpellCostIncreaseSourceEffect(int amount) {
        this(StaticValue.get(amount), null);
    }

    public SpellCostIncreaseSourceEffect(DynamicValue amount) {
        this(amount, null);
    }

    public SpellCostIncreaseSourceEffect(int amount, Condition condition) {
        this(StaticValue.get(amount), condition);
    }

    public SpellCostIncreaseSourceEffect(DynamicValue amount, Condition condition) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.amount = amount;
        this.condition = condition;
        StringBuilder sb = new StringBuilder();
        sb.append("this spell costs {").append(this.amount).append("} more to cast");
        if (this.condition != null) {
            sb.append(" ").append(this.condition.toString().startsWith("if ") ? "" : "if ");
            sb.append(this.condition.toString());
        }
        if (this.amount.toString().equals("X")) {
            sb.append(", where X is ").append(this.amount.getMessage());
        }
        this.staticText = sb.toString();
    }

    protected SpellCostIncreaseSourceEffect(final SpellCostIncreaseSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaCostsToIncrease = effect.manaCostsToIncrease;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToIncrease != null) {
            CardUtil.increaseCost((SpellAbility) abilityToModify, manaCostsToIncrease);
        } else {
            CardUtil.increaseCost(abilityToModify, this.amount.calculate(game, source, this));
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
    public SpellCostIncreaseSourceEffect copy() {
        return new SpellCostIncreaseSourceEffect(this);
    }
}
