package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.EffectType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class ActivateIfConditionActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    private String conditionText = null;

    public ActivateIfConditionActivatedAbility(Effect effect, Cost cost, Condition condition) {
        this(Zone.BATTLEFIELD, effect, cost, condition);
    }

    public ActivateIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
    }

    protected ActivateIfConditionActivatedAbility(final ActivateIfConditionActivatedAbility ability) {
        super(ability);
        this.conditionText = ability.conditionText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    public ActivateIfConditionActivatedAbility hideCondition() {
        return withConditionText("");
    }

    public ActivateIfConditionActivatedAbility withConditionText(String conditionText) {
        this.conditionText = conditionText;
        return this;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        if (condition.toString().startsWith("You may also")) {
            sb.append(' ').append(condition.toString()).append('.');
            return sb.toString();
        }
        if (condition instanceof InvertCondition) {
            sb.append(" You can't activate this ability ");
        } else {
            sb.append(" Activate only ");
        }
        if (!condition.toString().startsWith("during")
                && !condition.toString().startsWith("before")
                && !condition.toString().startsWith("if")) {
            sb.append("if ");
        }
        sb.append(condition.toString());
        if (timing == TimingRule.SORCERY) {
            sb.append(" and only as a sorcery");
        }
        sb.append('.');
        return sb.toString();
    }

    @Override
    public ActivateIfConditionActivatedAbility copy() {
        return new ActivateIfConditionActivatedAbility(this);
    }
}
